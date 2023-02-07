package erisnilton.dev.admin.catalogo.application.castmember.delete;

import erisnilton.dev.admin.catalogo.application.UnitUseCase;

public sealed abstract class DeleteCastMemberUseCase extends UnitUseCase<String> permits DefaultDeleteCastMemberUseCase {}
