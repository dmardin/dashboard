$(document).ready(
    function () {
        const urlParts = window.location.href.split('/');
        const jobName = urlParts[urlParts.length - 2];
        $.get("/ajax/"+jobName+"/newVersusFixedAggregatedTrendChart",
            function (builds) {
                renderTrendChart("new-versus-fixed-history-chart", builds, "");
            });
    });