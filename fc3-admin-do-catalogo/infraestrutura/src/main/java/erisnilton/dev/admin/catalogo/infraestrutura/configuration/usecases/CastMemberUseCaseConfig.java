package erisnilton.dev.admin.catalogo.infraestrutura.configuration.usecases;

import erisnilton.dev.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.create.DefaultCreateCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.retrieve.list.CastMemberListUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.retrieve.list.DefaultCastMemberListUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.update.DefaultUpdateCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import erisnilton.dev.admin.catalogo.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig( final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return  new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase(){
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public CastMemberListUseCase castMemberListUseCase() {
        return new DefaultCastMemberListUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }
}
