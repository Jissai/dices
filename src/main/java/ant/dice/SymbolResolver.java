package ant.dice;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class SymbolResolver {

    public Map<String, LinkedList<Symbol>> resolve(Collection<Symbol> symbols) {
        Map<String, LinkedList<Symbol>> groupedSymbols = symbols.stream()
                .collect(Collectors.groupingBy(Symbol::getId, Collectors.toCollection(LinkedList::new)));

        for (String entry : groupedSymbols.keySet()) {
            LinkedList<Symbol> entrySymbols = groupedSymbols.get(entry);
            for (Symbol item : entrySymbols) {
                if (groupedSymbols.containsKey(item.getOpposite())) {
                    LinkedList<Symbol> oppositeSymbols = groupedSymbols.get(item.getOpposite());
                    if (oppositeSymbols.poll() != null) {
                        entrySymbols.poll();
                    }
                }
            }
        }

        Map<String, LinkedList<Symbol>> result = groupedSymbols.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return result;
    }


}
