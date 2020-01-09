var dataSet = [{
    value: 4,
    name: '行业前景'
}, {
    value: 7,
    name: '职业发展'
}, {
    value: 7,
    name: '薪酬福利'
}, {
    value: 10,
    name: '企业文化'
}, {
    value: 13,
    name: '工作环境'
}, {
    value: 3,
    name: '工作强度'
}, {
    value: 31,
    name: '同事关系'
}, {
    value: 12,
    name: '家庭因素'
}, {
    value: 13,
    name: '其他'
}]
var option = {
    backgroundColor: '',

    title: {
        text: '',
        left: 'center',
        top: 20,
        textStyle: {
            color: '#ccc'
        }
    },

    tooltip: {
        trigger: 'item',
        axisPointer: {
            type: 'none'
        },
        backgroundColor: '#ffffff',
        textStyle: {
            color: 'rgb(102,102,102)',
            fontSize: '14'
        },
        extraCssText: 'box-shadow: 0px 4px 8px 2px rgba(67,33,21,.28)'
    },

    legend: {
        show: false,
        bottom: '30',
        width: '300'
    },

    visualMap: {
        show: false,
        min: 80,
        max: 600,
        inRange: {
            colorLightness: [0, 1]
        }
    },
    series: [
        {
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: dataSet.sort(function (a, b) {
                return a.value - b.value;
            }),
            roseType: '',
            label: {
                normal: {
                    textStyle: {
                        color: 'rgba(0,0,0,.7)'
                    }
                }
            },
            labelLine: {
                normal: {
                    lineStyle: {
                        color: 'rgba(0,0,0,.7)'
                    },
                    smooth: 0.2,
                    length: 10,
                    length2: 20
                }
            },
            itemStyle: {
                normal: {
                    color: function (params) {
                        //自定义颜色
                        var colorList = [];
                        dataSet.forEach((el, index) = > {
                            // 如果后台返回了颜色，就用后台颜色，否则自定义一个随机颜色不重复
                            if(el.color
                    )
                        {
                            colorList.push(el.color)
                        }
                    else
                        {
                            var r = Math.floor(Math.random() * 256);
                            var g = Math.floor(Math.random() * 256);
                            var b = Math.floor(Math.random() * 256);
                            var rgb = 'rgb(' + r + ',' + g + ',' + b + ')';
                            while (colorList.indexOf(rgb) > 0) {
                                r = Math.floor(Math.random() * 256);
                                g = Math.floor(Math.random() * 256);
                                b = Math.floor(Math.random() * 256);
                                rgb = 'rgb(' + r + ',' + g + ',' + b + ')';
                            }
                            colorList.push(rgb)
                        }
                    })
                        return colorList[params.dataIndex]
                    },
                    shadowBlur: 10,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            },
            minShowLabelAngle: 1,
            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function (idx) {
                return Math.random() * 200;
            }
        }
    ]
};
var myChart = echarts.init(document.getElementById('echarts-wrap'));
myChart.setOption(option);