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
                if(typeof params[0] !== "undefined"){
                    const colorSpan = color => '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + color + '"></span>';
                    let text = params[0].name; //heading of the Tooltip
                    let i;
                    for (i = 0; i < params.length; i++) {
                        if (params[i].seriesName !== "") {
                            const param = params[i];
                            text = text + '<br/>' + colorSpan(param.color) + ' ' + param.seriesName + ' : ' + param.value;
                        }
                    }
                    return text;
                }
            }
        },
        legend: {
            data: model.legend
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