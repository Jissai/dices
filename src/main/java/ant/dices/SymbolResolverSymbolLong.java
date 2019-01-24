package ant.dices;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SymbolResolverSymbolLong implements ISymbolResolver {

    @Override
    public Map<String, Long> resolve(Collection<IOpposable> symbols) {

        Collection<IOpposable> criticalEnrichedSymbols = enrichListWithCriticalEquivalence(symbols);
        Map<IOpposable, Long> groupedSymbols = countOccurences(criticalEnrichedSymbols);

        for (IOpposable key : groupedSymbols.keySet()) {
            Long currentOccurrences = groupedSymbols.get(key);
            if (currentOccurrences != null && currentOccurrences > 0) {

                Symbol target = new Symbol(key.getOpposite());
                Long oppositeOccurrences = groupedSymbols.get(target);

                if (oppositeOccurrences != null && oppositeOccurrences > 0) {
                    groupedSymbols.put(target, --oppositeOccurrences);
                    groupedSymbols.put(key, --currentOccurrences);
                }
            }

        }

        Map<String, Long> result = filterOutEmptyRecords(groupedSymbols);

        return result;
    }

    private Collection<IOpposable> enrichListWithCriticalEquivalence(Collection<IOpposable> symbols) {
        return symbols.stream()
            .collect(ArrayList::new, (ArrayList<IOpposable> accumulator, IOpposable item) -> {
                if (item instanceof IOpposableEquivalence) {
                    accumulator.addAll(((IOpposableEquivalence) item).getOpposableEquivalence());
                }
                accumulator.add(item);
            }, ArrayList::addAll);
    }

    private Map<IOpposable, Long> countOccurences(Collection<IOpposable> criticalEnrichedSymbols) {
        return criticalEnrichedSymbols.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    private Map<String, Long> filterOutEmptyRecords(Map<IOpposable, Long> groupedSymbols) {
        return groupedSymbols.entrySet().stream()
            .filter(entry -> !(entry.getValue() == 0))
            .collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue));
    }

}

