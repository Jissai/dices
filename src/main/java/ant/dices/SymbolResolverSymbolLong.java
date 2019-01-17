package ant.dices;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SymbolResolverSymbolLong implements ISymbolResolver {

    public Map<String, Long> resolve(Collection<Symbol> symbols) {
        Map<Symbol, Long> groupedSymbols = symbols.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        for (Symbol key : groupedSymbols.keySet()) {
            Long currentOccurencies = groupedSymbols.get(key);
            if (currentOccurencies == null || currentOccurencies <= 0) {
                continue;
            }

            Symbol target = new Symbol(key.getOpposite());
            Long oppositeOccurencies = groupedSymbols.get(target);

            if (oppositeOccurencies != null && oppositeOccurencies > 0) {
                groupedSymbols.put(target, --oppositeOccurencies);
                groupedSymbols.put(key, --currentOccurencies);
            }
        }

        Map<String, Long> result = groupedSymbols.entrySet().stream()
                .filter(entry -> !(entry.getValue() == 0))
                .collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue));

        return result;
    }

}

