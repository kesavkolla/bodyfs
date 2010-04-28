var maxValue = 220;
function DrawChart(data) {
	$('#mpiChart').gchart('destroy')
	$('#mpiChart')
			.gchart(
					{
						type : 'barVertGrouped',
						dataLabels : [ 'LU-5', 'P-7', 'HT-7', 'SI-8', 'TH-3', 'LI-6', 'SP-5', 'LIV', 'KI-1', 'BL-65',
								'GB-43', 'ST-41' ],
						legend : '',
						barWidth : 12,
						barSpacing : 5,
						barGroupSpacing : 10,
						maxValue : maxValue,
						height : 1000,
						width : 550,
						minValue : 40,
						encoding : 'scaled',
						axes : [
								$.gchart.axis('left', 40, maxValue),
								$.gchart.axis('top', [ 'LU', 'P', 'HT', 'SI', 'TH', 'LI', 'SP', 'LV', 'KI', 'BL', 'GB',
										'ST' ]),
								$.gchart.axis('right', [ data.low, data.average, data.high ],
										[ data.low, data.average, data.high ], 0, maxValue).ticks('blue',
										-$('#mpiChart').width()) ],
						series : [
								$.gchart.series('Reading1', [ data.LU1, data.p1, data.HT1, data.SI1, data.TH1,
										data.LI1, data.SP1, data.LV1, data.KI1, data.BL1, data.GB1, data.ST1 ], [
										'ff0080', 'ff0080', 'ff0080', 'ff0080', '000099', '8c001a', 'ff0080', 'b1b1b1',
										'7d9c9f', 'ff0080', '000099', '000099' ]),
								$.gchart.series('Reading2', [ data.LU2, data.p2, data.HT2, data.SI2, data.TH2,
										data.LI2, data.SP2, data.LV2, data.KI2, data.BL2, data.GB2, data.ST2 ], [
										'ff0080', 'ff0080', 'ff0080', 'ff0080', '000099', '8c001a', 'ff0080', 'b1b1b1',
										'7d9c9f', 'ff0080', '000099', '000099' ]) ],
						markers : [ $.gchart.marker('number', 'gray', 0, 'all'),
								$.gchart.marker('number', 'gray', 1, 'all') ]
					});
}
var colors = [ 'red', 'green', 'blue' ];
function DrawCompare(mpiarr) {
	$('#mpiChart').gchart('destroy')
	var series = new Array();
	var markers = new Array();
	for ( var i = 0, len = mpiarr.length; i < len; i++) {
		series[i] = $.gchart.series(mpiarr[i].examDateF, [ mpiarr[i].LU1, mpiarr[i].LU2, mpiarr[i].p1, mpiarr[i].p2,
				mpiarr[i].HT1, mpiarr[i].HT2, mpiarr[i].ST1, mpiarr[i].ST2, mpiarr[i].TH1, mpiarr[i].TH2,
				mpiarr[i].LI1, mpiarr[i].LI2, mpiarr[i].SP1, mpiarr[i].SP2, mpiarr[i].LV1, mpiarr[i].LV2,
				mpiarr[i].KI1, mpiarr[i].KI2, mpiarr[i].BL1, mpiarr[i].BL2, mpiarr[i].GB1, mpiarr[i].GB2,
				mpiarr[i].ST1, mpiarr[i].ST2 ], colors[i % 3]);
		markers[i] = $.gchart.marker('circle', colors[i % 3], i);
	}

	$('#mpiChart').gchart( {
		type : 'line',
		maxValue : 220,
		minValue : 40,
		height : 1000,
		width : 500,
		dataLabels : [ 'LU', 'P', 'HT', 'SI', 'TH', 'LI', 'SP', 'LIV', 'KI', 'BL', 'GB', 'ST' ],
		encoding : 'scaled',
		legend : 'right',
		series : series,
		axes : [ $.gchart.axis('left', 40, 220) ],
		markers : markers,
		margins : 30,
		gridSize : [ 20, 20 ],
		gridLine : [ 5, 10 ]
	});
}

/**
 * 
 * @return
 */
function printChart() {
	var buffer = new Array();
	buffer.push("<html><body>");
	buffer.push("<div style='800px;style:margin-left:auto;margin-right:auto;'>");
	buffer.push($("#mpiChart").html());
	buffer.push("</div>");
	buffer.push("<div style='text-align:center'><button onclick='window.print();'>Print</button></div>");
	buffer.push("</body></html>");
	var printWindow = window.open('', 'ChartWindow', 'width=600,height=600');
	printWindow.document.open();
	printWindow.document.write(buffer.join("\n"));
	printWindow.document.close();
}

/**
 * Show Legends
 */
function showLegends() {
	var printWindow = window.open('/EmiLegends.jsp', 'EMI-Legends', 'width=600,height=400');
}

/*
 * dataLabels : [ 'LU-1', 'LU-2', 'P-1', 'P-2', 'HT-1', 'HT-2', 'SI-1', 'SI-2', 'TH-1', 'TH-2', 'LI-1', 'LI-2', 'SP-1',
 * 'SP-2', 'LIV-1', 'LIV-2', 'KI-1', 'KI-2', 'BL-1', 'BL-2', 'GB-1', 'GB-2', 'ST-1', 'ST-2' ],
 */