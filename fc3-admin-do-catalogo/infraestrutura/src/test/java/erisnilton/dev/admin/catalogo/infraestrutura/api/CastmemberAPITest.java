package erisnilton.dev.admin.catalogo.infraestrutura.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import erisnilton.dev.admin.catalogo.ControllerTest;
import erisnilton.dev.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.retrieve.list.ListCastMemberUseCase;
import erisnilton.dev.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ControllerTest(controllers = CastmemberAPI.class)
public class CastmemberAPITest  {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateCastMemberUseCase createCastMemberUseCase;

    @MockBean
    private DeleteCastMemberUseCase deleteCastMemberUseCase;

    @MockBean
    private UpdateCastMemberUseCase updateCastMemberUseCase;

    @MockBean
    private GetCastMemberByIdUseCase getCastMemberByIdUseCase;

    @MockBean
    private ListCastMemberUseCase listCastMemberUseCase;
}
