$(document).ready(
    function () {
        /*
        Chart.defaults.global.elements.rectangle.backgroundColor = '#f7f1da';
        Chart.defaults.global.elements.rectangle.borderColor = '#355564';
        Chart.defaults.global.elements.rectangle.borderWidth = 1;

        var origin = $('#origin').text();
        var reference = $('#reference').text();
         */
        $.get("ajax/chartX"/*, {origin: origin, reference: reference}*/,
            function (builds) {
                // console.log("ajax is called for builds: ")
                // console.log(builds);
                /*
                new Chart($("#priorities-chart"), {
                    type: 'doughnut',
                    data: builds
                });
                */
                var view = 'hello';
                renderTrendChart('checkstyle-history-chart', builds, "");
            });
        /*$.get("ajax/pmd",
            function (builds) {
                console.log("ajax is called for builds: ")
                console.log(builds);

                var view = 'hello';
                renderTrendChart('pmd-history-chart', builds, "");
            });*/

    });

