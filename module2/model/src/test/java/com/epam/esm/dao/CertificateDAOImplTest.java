package com.epam.esm.dao;


import com.epam.esm.config.ConfigTestDB;
import com.epam.esm.dao.api.CertificateDAO;
import com.epam.esm.exception.certificate.NoSuchCertificateException;
import com.epam.esm.model.Certificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConfigTestDB.class)
@ActiveProfiles("test")
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:scripts/beforeTest.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:scripts/afterTest.sql")
})
class CertificateDAOImplTest {

    @Autowired
    CertificateDAO certificateDAOImpl;

    @Test
    public void shouldSaveCertificate() {
        String time = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
        long testId = 5L;
        Certificate expected = new Certificate("TestName", "TestDesc", 12.5, 12, time, time);
        certificateDAOImpl.save(expected);
        expected.setId(testId);
        Certificate actual = certificateDAOImpl.getByID(Math.toIntExact(testId));
        assertEquals(expected,actual);
    }

    @Test
    public void shouldReturnCertificateByIdPositive() {
        String time = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
        long testId = 4;
        Certificate expected = new Certificate("name4", "descr4", 8.0, 9, time, time);
        expected.setId(testId);
        Certificate actual = certificateDAOImpl.getByID(4);
        assertEquals(expected,actual);
    }

    @Test
    public void shouldReturnCertificateByIdNegative() {
        long testId = 5L;
        assertThrows(NoSuchCertificateException.class,()->{
            certificateDAOImpl.getByID(Math.toIntExact(testId));
        });
    }

    @Test
    public void shouldReturnAllCertificates() {
        int expectedSize = 4;
        int actualSize = certificateDAOImpl.getAll().size();
        assertEquals(expectedSize,actualSize);
    }

    @Test
    public void shouldTryDeleteCertificate_AndThanThrow_NoSuchCertificateException(){
        int testId = 4;
        certificateDAOImpl.delete(testId);
        assertThrows(NoSuchCertificateException.class,()->{
            certificateDAOImpl.getByID(Math.toIntExact(testId));
        });
    }

    @Test
    public void shouldReturnCertificatesByPartOfNamePositive(){
        String testName = "name2";
        boolean findingResult = certificateDAOImpl.findByNameOrDescription(testName).isEmpty();
        assertFalse(findingResult);
    }

    @Test
    public void shouldReturnCertificatesByPartOfNamePNegative(){
        String testName = "noSuchName";
        assertThrows(NoSuchCertificateException.class,()->{
            certificateDAOImpl.findByNameOrDescription(testName);
        });
    }

    @Test
    public void shouldUpdateCertificatePositive(){
        int id = 4;
        String time = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
        Certificate expected = new Certificate("newName", "newDescr", 10.0, 5, time, time);
        expected.setId((long) id);
        certificateDAOImpl.update(expected,id);
        Certificate actual = certificateDAOImpl.getByID(4);
        assertEquals(expected,actual);
    }

    @Test
    public void shouldTryUpdateCertificate_AndThanThrow_NoSuchCertificateException(){
        int noSuchId = 5;
        String time = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
        Certificate expected = new Certificate("newName", "newDescr", 10.0, 5, time, time);
        expected.setId((long) noSuchId);
        assertThrows(NoSuchCertificateException.class,()->{
            certificateDAOImpl.update(expected,noSuchId);
        });
    }

    @Test
    public void shouldPatchUpdateCertificatePositive(){
        int id = 4;
        String newName = "newName";
        int newDuration = 15;
        Map<String, Object> fieldsForUpdate = new HashMap<>();
        fieldsForUpdate.put("name",newName);
        fieldsForUpdate.put("duration",newDuration);
        String time = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
        Certificate expected = new Certificate(newName, "descr4", 8.0, newDuration, time, time);
        certificateDAOImpl.patchUpdate(4,fieldsForUpdate);
        Certificate actual = certificateDAOImpl.getByID(4);
    }

    @Test
    public void shouldPatchUpdateCertificateNegative(){
        int noSuchId = 5;
        String newName = "newName";
        int newDuration = 15;
        Map<String, Object> fieldsForUpdate = new HashMap<>();
        fieldsForUpdate.put("name",newName);
        fieldsForUpdate.put("duration",newDuration);
        String time = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE);
        Certificate expected = new Certificate(newName, "descr4", 8.0, newDuration, time, time);
        assertThrows(NoSuchCertificateException.class,()->{
            certificateDAOImpl.patchUpdate(noSuchId,fieldsForUpdate);
        });
    }


}
