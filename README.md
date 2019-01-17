ISymbolResolver.resolce take an unsorted list of Symbol and try to resolve opposite pair :
- if a Symbol has an occurence of its opposite they are both removed form the list
- if a symbol has no occurence of its opposite it remains in the list
- special Symbol are resolved if possible but their presence should be notified
