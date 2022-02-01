package com.epam.esm.service;


import com.epam.esm.dao.impl.CertificateDAOImpl;
import com.epam.esm.dao.impl.CertificateTagDAOImpl;
import com.epam.esm.dao.impl.TagDAOImpl;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.model.Certificate;
import com.epam.esm.model.CertificateTag;
import com.epam.esm.model.Tag;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {
    private List<Certificate> certificates = new ArrayList<>();
    private List<CertificateTag> certificateTags = new ArrayList<>();
    private Set<Tag> tags = new HashSet<>();
    @Mock
    private TagDAOImpl tagDAO = Mockito.mock(TagDAOImpl.class);
    @Mock
    private CertificateTagDAOImpl certificateTagDAO = Mockito.mock(CertificateTagDAOImpl.class);
    @Mock
    private CertificateDAOImpl certificateDAO = Mockito.mock(CertificateDAOImpl.class);

    @InjectMocks
    private CertificateServiceImpl service ;

    public CertificateServiceImplTest() {
    }

    @BeforeEach
    public void setup(){
        tags.add(new Tag(1L,"test"));
        tags.add(new Tag(2L,"test2"));
        tags.add(new Tag(3L,"test3"));
        tags.add(new Tag(4L,"test4"));
        Certificate certificate = new Certificate(1L,"TestName2", "TestDesc", 12.5, 12, "time", "time");
        Certificate certificate2 = new Certificate(2L,"TestName", "TestDesc2", 12.5, 12, "time2", "time2");
        Certificate certificate3 = new Certificate(3L,"TestNameSub", "TestDesc3", 1.5, 2, "time3", "time3");
        Certificate certificate4 = new Certificate(4L,"TestNameSub", "TestDesc4", 2.5, 1, "time4", "time4");
        certificates.add(certificate);
        certificates.add(certificate2);
        certificates.add(certificate3);
        certificates.add(certificate4);
        certificateTags.add(new CertificateTag(1L,1L,1L));
        certificateTags.add(new CertificateTag(2L,2L,1L));
        certificateTags.add(new CertificateTag(3L,3L,2L));
        certificateTags.add(new CertificateTag(4L,3L,2L));
        certificateTags.add(new CertificateTag(5L,4L,2L));

    }

    @Test
    void shouldReturnCertificatesOnlyByName(){
        when(certificateDAO.findByNameOrDescription("TestNameSub")).thenReturn(certificates.subList(2,3));
        when(certificateTagDAO.findAll()).thenReturn(certificateTags);
        List<Certificate> actual = service.search(null, "TestNameSub", null);
        assertEquals(actual.size(),certificates.subList(2,3).size());
    }

    @Test
    void shouldReturnCertificatesOnlyByNameNegative(){
        when(certificateDAO.findByNameOrDescription(anyString())).thenThrow(NoSuchCertificateException.class);
        assertThrows(NoSuchCertificateException.class,()->{
            service.search(null,"noSuchName",null);
        });
    }

    @Test
    void shouldReturnCertificatesOnlyByTag(){
        when(certificateDAO.findAll()).thenReturn(certificates);
        when(certificateTagDAO.findAll()).thenReturn(certificateTags);
        when(tagDAO.findByID(anyLong())).thenReturn(new Tag(2L,"test2"));
        List<Certificate> actual = service.search("test2", null, null);
        assertFalse(actual.isEmpty());
    }

    @Test
    void shouldReturnCertificatesOnlyByTagNegative(){
        when(certificateDAO.findAll()).thenThrow(NoSuchCertificateException.class);
        assertThrows(NoSuchCertificateException.class,()->{
            service.search("noSuchTag", null, null);
        });
    }

    @Test
    void shouldReturnCertificatesByASCSort(){
        when(certificateDAO.findAll()).thenReturn(certificates
                .stream().sorted(Comparator.comparing(Certificate::getName)).collect(Collectors.toList()));
        Certificate actual = service.search(null, null, "ASC").get(0);
        assertEquals(certificates.get(1),actual);
    }

    @Test
    void shouldReturnCertificatesByDESCSort(){
        when(certificateDAO.findAll()).thenReturn(certificates
                .stream().sorted(Comparator.comparing(Certificate::getName)).collect(Collectors.toList()));
        Certificate actual = service.search(null, null, "DESC").get(0);
        assertEquals(certificates.get(2),actual);
    }

    @Test
    void shouldReturnAllCertificates(){
        when(certificateDAO.findAll()).thenReturn(certificates);
        when(certificateTagDAO.findAll()).thenReturn(certificateTags);
        when(tagDAO.findByID(anyLong())).thenReturn(new Tag(1L,"test"),
        new Tag(2L,"test2"),
        new Tag(3L,"test3"),
        new Tag(4L,"test4"));
        assertEquals(service.getAll().size(),4);
    }

    @Test
    void shouldSaveCertificate_AndReturnCertificateWithID(){
        Certificate certificate = new Certificate("name", "desc", 12.5, 12, "time", "time");
        Certificate certificateFormDB = new Certificate(1L,"name", "desc", 12.5, 12, "time", "time");
        when(certificateDAO.findByID(1)).thenReturn(certificateFormDB);
        when(certificateTagDAO.findAll()).thenReturn(certificateTags);
        when(certificateDAO.save(certificate)).thenReturn(1L);
        when(tagDAO.findAll()).thenReturn(new ArrayList<>(tags));
        Long actualID = service.save(certificate).getId();
        String actualName = service.save(certificate).getName();
        assertAll(()->assertEquals(1,actualID),
                ()-> assertEquals("name",actualName));
    }

    @Test
    void shouldReturnCertificateByID(){
        Certificate certificateFormDB = new Certificate(1L,"name", "desc", 12.5, 12, "time", "time");
        when(certificateDAO.findByID(1)).thenReturn(certificateFormDB);
        when(certificateTagDAO.findAll()).thenReturn(certificateTags);
        when(tagDAO.findByID(anyLong())).thenReturn(new Tag(1L,"test"));
        Certificate actual = service.getByID(1);
        assertEquals(certificateFormDB,actual);
    }

    @Test
    void shouldUpdateCertificateByID(){
        Certificate certificate = new Certificate(1L,"name", "desc", 12.5, 12, "time", "time");
        Set<Tag> tags = new HashSet<>() ;
        tags.add(new Tag(1L,"test"));
        certificate.setTags(new HashSet<>(tags));
        when(tagDAO.findAll()).thenReturn(new ArrayList<>(tags));
        when(certificateDAO.findByID(1)).thenReturn(certificate);
        when(certificateTagDAO.findAll()).thenReturn(certificateTags);
        when(tagDAO.findTag(new Tag(1L,"test"))).thenReturn(new Tag(1L,"test"));
        when(tagDAO.findByID(anyLong())).thenReturn(new Tag(1L,"test"));
        long actualID = service.update(certificate, 1).getId();
        assertEquals(1,actualID);
    }

}
