package org.voi.analyzer;

import org.voi.models.Round;
import org.voi.models.summary.Summary;

public interface Analyzer<T extends Summary> {

    public T analyze(Round round);

}
