package erisnilton.dev.admin.catalogo.application.castmember.retrieve.get;

import erisnilton.dev.admin.catalogo.application.UseCase;

public sealed abstract class GetCastMemberByIdUseCase extends UseCase<String, CastMemberOutput> permits DefaultGetCastMemberByIdUseCase{
}
