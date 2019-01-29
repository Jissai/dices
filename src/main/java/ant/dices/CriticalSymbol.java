package ant.dices;

import java.util.Arrays;
import java.util.Collection;

public class CriticalSymbol extends AbstractSymbol implements IOpposableEquivalence {

    private IOpposable[] opposableEquivalence;

    public CriticalSymbol(final String id, final String opposite, IOpposable...equivalence) {
        super(id, opposite);
        this.opposableEquivalence = equivalence;
    }

    @Override
    public Collection<IOpposable> getOpposableEquivalence() {
        return Arrays.asList(this.opposableEquivalence);
    }
}
