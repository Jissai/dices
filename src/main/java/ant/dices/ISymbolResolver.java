package ant.dices;

import java.util.Collection;
import java.util.Map;

public interface ISymbolResolver {

    /**
     * Given a list of Symbols, try to find matching pairs of opposite Symbols and remove them from the final result.
     *
     * @param symbols unordered list of Symbols to resolve
     * @return a summary map of each symbol ID paired with their respective number of occurrence in the list
     */
    Map<String, Long> resolve(Collection<IOpposable> symbols);
}
