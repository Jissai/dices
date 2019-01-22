package ant.dices;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class SymbolResolverStringList implements ISymbolResolver {

    @Override
    public Map<String, Long> resolve(Collection<IOpposable> symbols) {
        Map<String, LinkedList<IOpposable>> groupedSymbols = symbols.stream()
            .collect(Collectors.groupingBy(IOpposable::getId, Collectors.toCollection(LinkedList::new)));

        for (String entry : groupedSymbols.keySet()) {
            LinkedList<IOpposable> entrySymbols = groupedSymbols.get(entry);
            for (IOpposable item : entrySymbols) {
                if (groupedSymbols.containsKey(item.getOpposite())) {
                    LinkedList<IOpposable> oppositeSymbols = groupedSymbols.get(item.getOpposite());
                    if (oppositeSymbols.poll() != null) {
                        entrySymbols.poll();
                    }
                }
            }
        }

        Map<String, Long> result = groupedSymbols.entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, e -> Long.valueOf(e.getValue().size())));

        return result;
    }

}
