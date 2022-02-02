import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.exception.tag.NoSuchTagException;
import com.epam.esm.exception.tag.TagAlreadyExistException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    private List<Tag> tags = new ArrayList<>();
    @Mock
    private TagDAOImpl tagDAO = Mockito.mock(TagDAOImpl.class);

    @InjectMocks
    private TagServiceImpl service;

    @BeforeEach
    public void setup() {
        tags.add(new Tag(1L, "test"));
        tags.add(new Tag(2L, "test2"));
        tags.add(new Tag(3L, "test3"));
        tags.add(new Tag(4L, "test4"));
    }

    @Test
    void shouldReturnAllTags(){
        when(tagDAO.findAll()).thenReturn(tags);
        int expected = tags.size();
        int actual = service.getAll().size();
        assertEquals(expected,actual);
        verify(tagDAO,times(1)).findAll();
    }

    @Test
    void shouldReturnTagByID(){
        Tag tag = new Tag(1L, "test");
        when(tagDAO.findByID(1)).thenReturn(tag);
        Tag actual = service.getByID(1);
        assertEquals(tag,actual);
    }

    @Test()
    void shouldReturnTagByID_Negative(){
        doThrow(new NoSuchTagException())
        .when(tagDAO).findByID(1);
        assertThrows(NoSuchTagException.class,()->{
            service.getByID(1);
        });
    }

    @Test
    void shouldTagByID(){
        doNothing().when(tagDAO).delete(anyInt());
        service.delete(1);
        verify(tagDAO,times(1)).delete(1);
    }

    @Test()
    void shouldDeleteTagByID_Negative(){
        doThrow(new NoSuchTagException()).when(tagDAO).delete(1);
        assertThrows(NoSuchTagException.class,()->{
            service.delete(1);
        });
    }

    @Test
    void shouldSaveTag_andReturnWithID(){
        Tag tag = new Tag(1L, "test");
        when(tagDAO.save(tag)).thenReturn(Long.valueOf(1));
        Tag actual = service.save(tag);
        assertEquals(tag,actual);
    }

    @Test
    void shouldSaveTag_andReturnWithID_Negative(){
        Tag tag = new Tag(1L, "test");
        doThrow(new TagAlreadyExistException()).when(tagDAO).save(tag);
        assertThrows(TagAlreadyExistException.class,()->{
            service.save(tag);
        });
    }



}
