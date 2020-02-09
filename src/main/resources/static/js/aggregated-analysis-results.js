$(document).ready(
    function () {
        const urlParts = window.location.href.split('/');
        const jobName = urlParts[urlParts.length - 2];
        $.get("/ajax/aggregatedAnalysisResults/"+jobName/*, {origin: origin, reference: reference}*/,
            function (builds) {
                renderTrendChart('aggregated-analysis-results-history-chart', builds, "");
            });
    });

