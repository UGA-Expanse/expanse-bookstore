package rocks.j5.uga.expanse.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EncounteredError
{
    @NotNull
    private String message;
    private Integer number;
}
