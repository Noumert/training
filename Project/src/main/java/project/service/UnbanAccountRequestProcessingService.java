package project.service;

import project.entity.UnbanAccountRequest;

/**
 * Created by Noumert on 21.08.2021.
 */
public interface UnbanAccountRequestProcessingService {
    void unbanAndSetResolvedByRequest(boolean ban, boolean resolved, UnbanAccountRequest unbanAccountRequest);
}
