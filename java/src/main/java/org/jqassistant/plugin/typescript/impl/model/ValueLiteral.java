package org.jqassistant.plugin.typescript.impl.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ValueLiteral extends Value{

    private Object value;

}
