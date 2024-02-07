package org.jqassistant.plugin.typescript.impl.mapper.core;

import com.buschmais.jqassistant.core.scanner.api.Scanner;
import com.buschmais.jqassistant.plugin.common.api.mapper.DescriptorMapper;
import org.jqassistant.plugin.typescript.api.model.core.FunctionDeclarationDescriptor;
import org.jqassistant.plugin.typescript.impl.model.core.FunctionDeclaration;
import org.mapstruct.*;

import java.util.List;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(uses = {TypeMapper.class, ParameterDeclarationMapper.class})
public interface FunctionDeclarationMapper extends
        DescriptorMapper<FunctionDeclaration, FunctionDeclarationDescriptor> {

    FunctionDeclarationMapper INSTANCE = getMapper(FunctionDeclarationMapper.class);

    @BeforeMapping
    default void init(FunctionDeclaration value, @MappingTarget FunctionDeclarationDescriptor target, @Context Scanner scanner) {
        scanner.getContext().peek(TypeParameterResolver.class).pushScope();
    }

    @Override
    @Mapping(source = "functionName", target = "name")
    @Mapping(source = "coordinates.fileName", target = "fileName")
    @Mapping(source = "coordinates.startLine", target = "startLine")
    @Mapping(source = "coordinates.startColumn", target = "startColumn")
    @Mapping(source = "coordinates.endLine", target = "endLine")
    @Mapping(source = "coordinates.endColumn", target = "endColumn")
    @Mapping(target = "dependents", ignore = true)
    @Mapping(target = "dependencies", ignore = true)
    @Mapping(target = "exporters", ignore = true)
    @Mapping(target = "parameters", dependsOn = "typeParameters")
    FunctionDeclarationDescriptor toDescriptor(FunctionDeclaration value, @Context Scanner scanner);

    @AfterMapping
    default void teardown(FunctionDeclaration type, @MappingTarget FunctionDeclarationDescriptor target, @Context Scanner scanner) {
        scanner.getContext().peek(FqnResolver.class).registerGlobalFqn(target);
        scanner.getContext().peek(TypeParameterResolver.class).popScope();
    }

    List<FunctionDeclarationDescriptor> mapList(List<FunctionDeclaration> value, @Context Scanner scanner);
}
