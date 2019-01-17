package ant.dices;

import java.util.Collection;
import java.util.Map;

public interface ISymbolResolver {
    Map<String, Long> resolve(Collection<Symbol> symbols);
}
