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

        // grouping
        Map<IOpposable, Long> groupedSymbols = criticalEnrichedSymbols.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (IOpposable key : groupedSymbols.keySet()) {
            Long currentOccurrences = groupedSymbols.get(key);
            if (currentOccurrences == null || currentOccurrences <= 0) {
                continue;
            }

            Symbol target = new Symbol(key.getOpposite());
            Long oppositeOccurencies = groupedSymbols.get(target);

            if (oppositeOccurencies != null && oppositeOccurencies > 0) {
                groupedSymbols.put(target, --oppositeOccurencies);
                groupedSymbols.put(key, --currentOccurrences);
            }
        }

        Map<String, Long> result = groupedSymbols.entrySet().stream()
            .filter(entry -> !(entry.getValue() == 0))
            .collect(Collectors.toMap(e -> e.getKey().getId(), Map.Entry::getValue));

        return result;
    }

    private Collection<IOpposable> enrichListWithCriticalEquivalence(Collection<IOpposable> symbols) {
        return symbols.stream()
                .collect(ArrayList::new, (ArrayList<IOpposable> accumulator, IOpposable opposable) -> {
                    if (opposable instanceof IOpposableEquivalence) {
                        accumulator.addAll(((IOpposableEquivalence) opposable).getOpposableEquivalence());
                    }
                    accumulator.add(opposable);
                }, ArrayList::addAll);
    }

}

