function grafik(StringObject, i) {
    var model = JSON.parse(StringObject);
    var id = "grafik"+i;
    var chartPlaceHolder = document.getElementById(id);
    var chart = echarts.init(chartPlaceHolder);
    chartPlaceHolder.echart = chart;
    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            formatter: function (params) {
                var colorSpan = color => '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:' + color + '"></span>';
                var tar = params[1].name;
                var i;
                for (i = 0; i < params.length; i++) {
                    if(i != 4){
                        var param = params[i];
                        tar = tar + '<br/>' + colorSpan(param.color) +' '+ param.seriesName + ' : ' + param.value;
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