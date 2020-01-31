package edu.hm.hafner.warningsngui.service;

import edu.hm.hafner.warningsngui.dto.Build;
import edu.hm.hafner.warningsngui.dto.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    public List<String> getUsedToolsFromBuild(Build build){
        return build.getResults().stream().map(Result::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<String> getInfoMessagesFromResultWithToolId(Build build, String toolId) {
        return getResultFromBuildWithToolId(build, toolId).getInfoMessages();
    }

    public List<String> getErrorMessagesFromResultWithToolId(Build build, String toolId) {
        return getResultFromBuildWithToolId(build, toolId).getErrorMessages();
    }

    public Result getResultByToolName(Build build, String toolName){
        return build.getResults().stream().filter(r -> r.getName().equals(toolName)).findFirst().get();
    }

    private Result getResultFromBuildWithToolId(Build build, String toolId) {
        return build.getResults().stream().filter(r -> r.getName().equals(toolId)).findFirst().get();
    }
}
