package be.renaud11232.awesomecommand.annotation.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@SupportedAnnotationTypes("be.renaud11232.awesomecommand.annotation.args.*")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ArgAnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        annotations.forEach(annotation -> ElementFilter.fieldsIn(roundEnv.getElementsAnnotatedWith(annotation)).forEach(field -> {
            List<DeclaredType> foundAnnotations = field.getAnnotationMirrors()
                    .stream()
                    .map(AnnotationMirror::getAnnotationType)
                    .filter(annotationType -> annotationType.toString().startsWith("be.renaud11232.awesomecommand.annotation"))
                    .collect(Collectors.toList());
            if (foundAnnotations.size() > 1) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, String.format("Field %s has incompatible annotations %s and %s", field, foundAnnotations.get(0), foundAnnotations.get(1)), field);
            }
        }));
        return true;
    }

}
