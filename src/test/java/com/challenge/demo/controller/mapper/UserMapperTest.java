package com.challenge.demo.controller.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.challenge.demo.controller.dto.PhoneDTO;
import com.challenge.demo.controller.dto.UserDTO;
import com.challenge.demo.controller.dto.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.challenge.demo.service.model.Phone;
import com.challenge.demo.service.model.User;
import org.springframework.stereotype.Component;

@Component
class UserMapperTest {

    @Mock
    private User userMock;

    @InjectMocks
    private UserMapper userMapper;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("johndoe@example.com");
        userDTO.setPassword("password");

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber("555-1234");
        phoneDTO.setCityCode("123");
        phoneDTO.setCountryCode("US");

        List<PhoneDTO> phones = new ArrayList<>();
        phones.add(phoneDTO);
        userDTO.setPhones(phones);

        User user = userMapper.toUser(userDTO);

        assertEquals(user.getName(), userDTO.getName());
        assertEquals(user.getEmail(), userDTO.getEmail());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertEquals(user.getPhones().size(), userDTO.getPhones().size());
    }

    @Test
    public void testToUser_Failed() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("John Doe");
        userDTO.setEmail("john.doe@example.com");
        userDTO.setPassword("password");
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        phonesDTO.add(new PhoneDTO("123456789", "1", "1"));
        userDTO.setPhones(phonesDTO);

        User user = UserMapper.toUser(userDTO);
        assertNotEquals(user.getName(), "Jane Doe");
    }

    @Test
    public void testToUsersResponse() {
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();

        when(userMock.getId()).thenReturn(uuid);
        when(userMock.getCreatedAt()).thenReturn(now);
        when(userMock.getUpdatedAt()).thenReturn(now);
        when(userMock.getLastLogin()).thenReturn(now);
        when(userMock.getToken()).thenReturn("token");
        when(userMock.isActive()).thenReturn(true);

        Phone phone = new Phone();
        phone.setNumber("555-1234");
        phone.setCityCode("123");
        phone.setCountryCode("US");

        List<Phone> phones = new ArrayList<>();
        phones.add(phone);

        when(userMock.getPhones()).thenReturn(phones);

        UserResponse userResponse = userMapper.toUsersResponse(userMock);

        assertEquals(userResponse.getId(), uuid.toString());
        assertEquals(userResponse.getCreated(), now);
        assertEquals(userResponse.getModified(), now);
        assertEquals(userResponse.getLastLogin(), now);
        assertEquals(userResponse.getToken(), "token");
        assertEquals(userResponse.isActive(), true);
        assertEquals(userResponse.getPhones().size(), 1);
    }
}