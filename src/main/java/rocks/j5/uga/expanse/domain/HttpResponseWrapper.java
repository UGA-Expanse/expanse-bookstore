package rocks.j5.uga.expanse.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import rocks.j5.uga.expanse.model.User;

import java.util.Collection;
import java.util.Collections;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HttpResponseWrapper<T> {
    T content;
    @Builder.Default
    Collection<EncounteredError> encounteredErrors = Collections.emptyList();
}


