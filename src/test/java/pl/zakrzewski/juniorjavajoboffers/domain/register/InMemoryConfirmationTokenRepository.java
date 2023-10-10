package pl.zakrzewski.juniorjavajoboffers.domain.register;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationToken;
import pl.zakrzewski.juniorjavajoboffers.domain.register.token.ConfirmationTokenRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class InMemoryConfirmationTokenRepository implements ConfirmationTokenRepository {

    Map<String, ConfirmationToken> db = new ConcurrentHashMap<>();
    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        return Optional.ofNullable(db.get(token));
    }

    @Override
    public void deleteConfirmationTokenByUser_Id(String userId) {
        Optional<ConfirmationToken> token = db.values()
                .stream()
                .filter(t -> t.getUser().getId().equals(userId))
                .findFirst();
        String tokenId = token.get().getToken();
        db.remove(tokenId);
    }

    @Override
     public <S extends ConfirmationToken> S save(S entity) {
        UUID id = UUID.randomUUID();
        ConfirmationToken confirmationToken = ConfirmationToken.builder()
                .id(id.toString())
                .token(entity.getToken())
                .createdAt(entity.getCreatedAt())
                .expiresAt(entity.getExpiresAt())
                .user(entity.getUser())
                .build();
        db.put(confirmationToken.getToken(), confirmationToken);
        return (S) confirmationToken;
    }

    @Override
    public int updateConfirmedAt(String token, LocalDateTime now) {
            ConfirmationToken confirmationToken = db.get(token);
            confirmationToken.setConfirmedAt(now);
            db.replace(token, confirmationToken);
        return 0;
    }

    @Override
    public void flush() {

    }
    @Override
    public <S extends ConfirmationToken> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ConfirmationToken> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ConfirmationToken getOne(String s) {
        return null;
    }

    @Override
    public ConfirmationToken getById(String s) {
        return null;
    }

    @Override
    public ConfirmationToken getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ConfirmationToken> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ConfirmationToken> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ConfirmationToken> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ConfirmationToken, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }



    @Override
    public <S extends ConfirmationToken> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ConfirmationToken> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<ConfirmationToken> findAll() {
        return null;
    }

    @Override
    public List<ConfirmationToken> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(ConfirmationToken entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends ConfirmationToken> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<ConfirmationToken> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ConfirmationToken> findAll(Pageable pageable) {
        return null;
    }
}
