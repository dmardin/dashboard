function resultSummarize(model, i) {
    const id = "result-summarize-" + i;
    const chartPlaceHolder = document.getElementById(id);
    const chart = echarts.init(chartPlaceHolder);
    chartPlaceHolder.echart = chart;
    const option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            formatter: function (params) {
                const colorSpan = color => '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + color + '"></span>';
                let tar = params[1].name;
                let i;
                //TODO fix bug with selection in grafic
                for (i = 0; i < params.length; i++) {
                    if (i !== 4) {
                        const param = params[i];
                        tar = tar + '<br/>' + colorSpan(param.color) + ' ' + param.seriesName + ' : ' + param.value;
                    }
                }
                return tar;
            }
        },
        legend: {
            data: model.legendData
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        yAxis: [
            {
                type: 'category',
                data: ['Result'],
                show: false
            }
        ],
        xAxis: [
            {
                type: 'value',
                show: false
            }
        ],
        series: model.series
    };
    chart.setOption(option);

}