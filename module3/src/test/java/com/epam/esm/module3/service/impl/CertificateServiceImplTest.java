package com.epam.esm.module3.service.impl;

import com.epam.esm.module3.model.dao.impl.CertificateDAOImpl;
import com.epam.esm.module3.model.dao.impl.TagDAOImpl;
import com.epam.esm.module3.model.entity.Certificate;
import com.epam.esm.module3.model.entity.Tag;
import com.epam.esm.module3.service.exception.NoSuchCertificateException;
import com.epam.esm.module3.service.exception.UniqueNameCertificateException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {
    private static final List<Certificate> certificates = new ArrayList<>();
    private static final List<Tag> tags = new ArrayList<>();

    @Mock
    private CertificateDAOImpl certificateDAO = Mockito.mock(CertificateDAOImpl.class);

    @Mock
    private TagDAOImpl tagDAO = Mockito.mock(TagDAOImpl.class);

    @InjectMocks
    private CertificateServiceImpl service;

    @BeforeAll
    public static void setup() {
        certificates.add(new Certificate());
        certificates.add(new Certificate());
        certificates.add(new Certificate());
        certificates.add(new Certificate());
        certificates.add(new Certificate());

        tags.add(new Tag());
        tags.add(new Tag());
        tags.add(new Tag());
    }

    @Test
    void shouldReturnCertificateById(){
        Certificate certificate = new Certificate();
        certificate.setId(1L);

        when(certificateDAO.findByID(certificate.getId())).thenReturn(Optional.of(certificate));

        Certificate expected = certificate;
        Certificate actual = service.getByID(certificate.getId());
        assertEquals(expected,actual);
    }

    @Test
    void shouldThrow_NoSuchCertificateException(){
        Certificate certificate = new Certificate();
        certificate.setId(1L);

        doThrow(new NoSuchCertificateException()).when(certificateDAO).findByID(certificate.getId());
        assertThrows(NoSuchCertificateException.class,()->{
            service.getByID(certificate.getId());
        });
    }

    @Test
    void shouldReturnOrders(){
        Pageable pageable = PageRequest.of(0, 10);

        when(certificateDAO.findAll(pageable)).thenReturn(certificates);

        int expected = certificates.size();
        int actual = service.getAll(0,10).size();
        assertEquals(expected,actual);
        verify(certificateDAO,times(1)).findAll(pageable);
    }

    @Test
    void shouldDeleteCertificate(){
        Certificate certificate = new Certificate();

        doNothing().when(certificateDAO).delete(anyLong());
        when(certificateDAO.findByID(anyLong())).thenReturn(Optional.of(certificate));

        service.delete(anyLong());

        verify(certificateDAO,times(1)).delete(anyLong());
    }

    @Test
    void shouldThrow_NoSuchCertificateExceptionInDelete(){
        doThrow(new NoSuchCertificateException()).when(certificateDAO).findByID(anyLong());
        assertThrows(NoSuchCertificateException.class,()->{
            service.delete(anyLong());
        });
    }

    @Test
    void shouldSaveCertificate(){
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setTags(new HashSet<>(tags.subList(1,2)));

        when(certificateDAO.save(certificate)).thenReturn(certificate);
        when(tagDAO.findTags()).thenReturn(tags);

        Certificate expected = service.save(certificate);

        verify(certificateDAO,times(1)).save(certificate);
        assertNotNull(expected.getTags());
    }

    @Test
    void shouldThrow_NoSuchCertificateException_InUpdate(){
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setTags(new HashSet<>(tags));

        when(certificateDAO.findByID(certificate.getId())).thenReturn(Optional.empty());

        assertThrows(NoSuchCertificateException.class,()->{
            service.update(certificate,certificate.getId());
        });

    }

    @Test
    void shouldThrow_UniqueNameCertificateException_InUpdate(){
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setTags(new HashSet<>(tags));

        when(certificateDAO.findByID(certificate.getId())).thenReturn(Optional.of(certificate));
        when(certificateDAO.findByName(certificate)).thenReturn(Optional.of(certificate));

        assertThrows(UniqueNameCertificateException.class,()->{
            service.update(certificate,certificate.getId());
        });
    }

    @Test
    void shouldUpdateCertificate(){
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        certificate.setName("test");
        certificate.setPrice(11.1);
        certificate.setDescription("desc");
        certificate.setTags(new HashSet<>(tags));

        when(certificateDAO.update(certificate)).thenReturn(certificate);
        when(certificateDAO.findByID(certificate.getId())).thenReturn(Optional.of(certificate));
        when(certificateDAO.findByName(certificate)).thenReturn(Optional.empty());

        Certificate expected = service.update(certificate, certificate.getId());

        verify(certificateDAO,times(1)).update(certificate);
        assertNotNull(expected);
        assertEquals(certificate,expected);

    }
}
