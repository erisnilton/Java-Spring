package erisnilton.dev.admin.catalogo.application.castmember.update;

import erisnilton.dev.admin.catalogo.application.UseCase;

public sealed abstract class UpdateCastMemberUseCase extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput> permits DefaultUpdateCastMemberUseCase {
}
