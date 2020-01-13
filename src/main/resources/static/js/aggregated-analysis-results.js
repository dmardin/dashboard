$(document).ready(
    function () {
        const parts = window.location.href.split('/');
        //TODO doppelter pop - kann man /build nicht in der url einfach entfernen?
        parts.pop();
        const lastSegment = parts.pop() || parts.pop();
        $.get("/ajax/aggregatedAnalysisResults/"+lastSegment/*, {origin: origin, reference: reference}*/,
            function (builds) {
                renderTrendChart('aggregated-analysis-results-history-chart', builds, "");
            });
    });

