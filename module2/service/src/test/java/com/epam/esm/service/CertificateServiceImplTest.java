package com.epam.esm.service;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {
    private static List<Certificate> certificates = new ArrayList<>();
    private static Set<Tag> tags = new HashSet<>();

    @Mock
    private CertificateDAO dao;

    @InjectMocks
    private CertificateService service = new CertificateServiceImpl(dao);

    @BeforeAll
    static public void setup(){
        tags.add(new Tag(1L,"test"));
        tags.add(new Tag(2L,"test2"));
        tags.add(new Tag(3L,"test3"));
        tags.add(new Tag(4L,"test4"));
        Certificate certificate = new Certificate("TestName2", "TestDesc", 12.5, 12, "time", "time");
        Certificate certificate2 = new Certificate("TestName", "TestDesc2", 12.5, 12, "time2", "time2");
        Certificate certificate3 = new Certificate("TestNameSub", "TestDesc3", 1.5, 2, "time3", "time3");
        Certificate certificate4 = new Certificate("TestNameSub", "TestDesc4", 2.5, 1, "time4", "time4");
        certificates.add(certificate);
        certificates.add(certificate2);
        certificates.add(certificate3);
        certificates.add(certificate4);
        certificates.forEach(c->c.setTags(tags));

    }

    @Test
    void shouldReturnCertificatesOnlyByName(){
        when(dao.findByNameOrDescription("TestNameSub")).thenReturn(certificates.subList(2,3));
        List<Certificate> actual = service.search(null, "TestNameSub", null);
        assertEquals(actual.size(),certificates.subList(2,3).size());
    }

    @Test
    void shouldReturnCertificatesOnlyByNameNegative(){
        when(dao.findByNameOrDescription(anyString())).thenThrow(NoSuchCertificateException.class);

        assertThrows(NoSuchCertificateException.class,()->{
            service.search(null,"noSuchName",null);
        });
    }

    @Test
    void shouldReturnCertificatesOnlyByTag(){
        when(dao.getAll()).thenReturn(certificates);
        List<Certificate> actual = service.search("test2", null, null);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnCertificatesOnlyByTagNegative(){
        when(dao.getAll()).thenThrow(NoSuchCertificateException.class);
        assertThrows(NoSuchCertificateException.class,()->{
            service.search("noSuchTag", null, null);
        });
    }

    @Test
    void shouldReturnCertificatesByASCSort(){
        when(dao.getAll()).thenReturn(certificates
                .stream().sorted(Comparator.comparing(Certificate::getName)).collect(Collectors.toList()));
        Certificate actual = service.search(null, null, "ASC").get(0);
        assertEquals(certificates.get(1),actual);
    }

    @Test
    void shouldReturnCertificatesByDESCSort(){
        when(dao.getAll()).thenReturn(certificates
                .stream().sorted(Comparator.comparing(Certificate::getName)).collect(Collectors.toList()));
        Certificate actual = service.search(null, null, "DESC").get(0);
        assertEquals(certificates.get(2),actual);
    }

}
