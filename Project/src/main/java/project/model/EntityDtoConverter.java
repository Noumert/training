package project.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import project.dto.*;
import project.entity.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

public interface EntityDtoConverter<E, D> {

    D convertEntityToDto(E entity);

    E convertDtoToEntity(D dto);

    List<D> convertEntityListToDtoList(List<E> payments);

    List<E> convertDtoListToPaymentList(List<D> paymentDTOS);

    Page<D> convertEntityPageToDtoPage(Page<E> payments);

    Page<E> convertDtoPageToEntityPage(Page<D> payments);
}
