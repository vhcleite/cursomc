package com.vleite.cursomc.services;

import com.vleite.cursomc.domain.Cidade;
import com.vleite.cursomc.domain.Cliente;
import com.vleite.cursomc.domain.Endereco;
import com.vleite.cursomc.domain.enums.Perfil;
import com.vleite.cursomc.dto.ClienteDTO;
import com.vleite.cursomc.dto.NewClienteDTO;
import com.vleite.cursomc.repositories.CidadeRepository;
import com.vleite.cursomc.repositories.ClienteRepository;
import com.vleite.cursomc.repositories.EnderecoRepository;
import com.vleite.cursomc.security.UserSS;
import com.vleite.cursomc.services.exceptions.AuthorizationException;
import com.vleite.cursomc.services.exceptions.DataIntegrityException;
import com.vleite.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    CidadeRepository cidadeRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Value("${img.prefix.client.profile}")
    private String prefix;
    @Autowired
    private ImageService imagService;

    @Autowired
    private S3Service s3Service;

    public Cliente find(Integer id) {

        UserSS user = UserService.authenticated();

        if (user == null || (!user.hasRole(Perfil.ADMIN) && id != user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = clienteRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                String.format("Objeto não encontrado! Id: %d, Tipo: %s", id, Cliente.class.getCanonicalName())));
    }

    public Cliente findByEmail(String email) {

        UserSS user = UserService.authenticated();

        if (user == null || (!user.hasRole(Perfil.ADMIN) && !user.getUsername().equals(email))) {
            throw new AuthorizationException("Acesso negado");
        }

        Cliente obj = clienteRepository.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException(
                    String.format("Objeto não encontrado! Email: %s, Tipo: %s", email, Cliente.class.getName()));
        }
        return obj;
    }

    public Collection<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente toCliente(ClienteDTO obj) {
        return new Cliente(obj.getId(), obj.getNome(), obj.getEmail());
    }

    public Cliente toCliente(@Valid NewClienteDTO obj) {
        Cliente client = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
                obj.getTipo(), passwordEncoder.encode(obj.getSenha()));

        Optional<Cidade> optCidade = cidadeRepository.findById(obj.getCidadeId());
        Endereco endereco = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
                obj.getBairro(), obj.getCep(), client, optCidade.get());

        client.getEnderecos().add(endereco);

        client.addTelefone(obj.getTelefone1());
        client.addTelefone(obj.getTelefone2());
        client.addTelefone(obj.getTelefone3());

        return client;

    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = clienteRepository.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente client = find(obj.getId());
        updateClient(client, obj);
        return clienteRepository.save(client);
    }

    private void updateClient(Cliente client, Cliente obj) {
        client.setNome(obj.getNome());
        client.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {
            clienteRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataIntegrityException("Não é possível excluir pois há entidades relacionadas.");
        }
    }

    public Page<Cliente> findPage(int page, int size, String direction, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), sortBy);
        return clienteRepository.findAll(pageRequest);
    }

    public Cliente getByEmail(String username) {
        return clienteRepository.findByEmail(username);
    }

    public URI uploadClientPicture(MultipartFile file) {
        UserSS user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage image = imagService.getImageFromFile(file);
        image = imagService.resize(image, 200);
        String filename = prefix + user.getId() + ".jpg";
        URI uri = s3Service.uploadFile(imagService.getInputStream(image, "jpg"), filename, "jpg");

        Optional<Cliente> clientOpt = clienteRepository.findById(user.getId());
        Cliente cliente = clientOpt.get();
        cliente.setFileUrl(uri.toString());
        clienteRepository.save(cliente);

        return uri;
    }
}
