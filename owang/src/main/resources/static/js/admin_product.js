// 캔버스 크기 설정
function initialize(){
	graph.width=500;
	graph.height=300;
}
initialize();

// 그래프 데이터
var graphInfo = {
	title: "일주일간 일매출",
	data: [
		// 날짜, 매출액
		// 이런 형식으로 가져오면 됨
		// 15000: 임시로 내가 책정한 상품들 가격
		{date: "9월1일", amount: 23*15000},
		{date: "2일", amount: 26*15000},
		{date: "3일", amount: 26*15000},
		{date: "4일", amount: 29*15000},
		{date: "5일", amount: 26*15000},
		{date: "6일", amount: 28*15000},
		{date: "7일", amount: 25*15000}
	]
}

// 그래프1 그리기
function drawGraph(){
	var ctx = graph.getContext("2d");
	var data = graphInfo;
    var graphTop = 50; // 그래프의 최상단 위치 - 화면영역 상단에서 20px 밑에서 그리기 시작
    var graphHeight = graph.height - graphTop - 50; // 그래프의 세로 범위 높이
    var minValue = 0;
    var maxValue = 0;

	// 데이터에서 최소값과 최대값 찾기
	// 데이터의 범위를 돌면서
	for (var i = 0; i < data.data.length; i++) {
		// 최소값과 최대값 초기값을 첫번째값으로 정함
		if(i==0) {			
			minValue=data.data[i].amount;
			maxValue=data.data[i].amount;
		}else {	
			// 값을 비교해서 최소값과 최대값을 정함	
		    if (data.data[i].amount < minValue) {
		        minValue = data.data[i].amount;
		    }
		    if (data.data[i].amount > maxValue) {
		        maxValue = data.data[i].amount;
		    }
		}
	}
	minValue -= 15000;
    maxValue += 15000;

	// 배경
	// 배경색
	ctx.fillStyle = "white";
	// 사각형으로 채움
	ctx.fillRect(0, 0, graph.width, graph.height);

    // 그래프 제목 추가
    ctx.fillStyle = "black";
    ctx.font = "18px Arial";
    ctx.textAlign = "center";
    ctx.fillText(data.title, graph.width / 2, 20);

	// x축 레이블(날짜)
    ctx.fillStyle = "black";
	ctx.font = "12px Arial";
	ctx.textAlign = "center";
	ctx.textBaseline = "top";
	for (var i = 0; i < data.data.length; i++) {
	    var x = 100 + i * 50;
	    var y = graphTop + graphHeight * (1 - ((data.data[i].amount - minValue) / (maxValue - minValue)));
	    if (i == 0) {
	        ctx.beginPath();
	        ctx.moveTo(x, y);
	    } else {
	        ctx.lineTo(x, y);
	    }
	    // 날짜를 텍스트로 표시
	    ctx.fillText(data.data[i].date, x, graph.height -30);
	}

	// y축 레이블(눈금선) 
	ctx.beginPath();
	ctx.font = "12px Arial";
	ctx.textAlign = "right";
	// 텍스트의 세로정렬 - 텍스트의 중간줄
	ctx.textBaseline = "middle";
	
    // y축 눈금선 간격
    var interval = 15000;

    // y축 눈금선 그리기
    for (var i = minValue; i <= maxValue; i += interval) {
        var y = graphTop + graphHeight * (1 - ((i - minValue) / (maxValue - minValue)));
        ctx.moveTo(50, y);
        ctx.lineTo(graph.width - 50, y);
        ctx.fillText(i, 50, y);
    }
    // 가로 눈금선 색
    ctx.strokeStyle = "rgba(0, 0, 0, 0.1)";
    ctx.stroke();

	// 꺾은선 그래프
    for (var i = 0; i < data.data.length; i++) {
		// 텍스트는 그래프 왼쪽끝에서 110px부터, 인덱스끼리는 50px정도의 거리를 두고 그려짐
        var x = 100 + i * 50;
		var y = graphTop + graphHeight * (1 - ((data.data[i].amount - minValue) / (maxValue - minValue)));
        if (i == 0) {
			// 꺾은선 그래프 그리기 시작
            ctx.beginPath();
            ctx.moveTo(x, y);
        } else {
            ctx.lineTo(x, y);
        }
    }
    ctx.strokeStyle = "orange"; // 꺾은선 그래프의 선 색상 설정
    ctx.lineWidth = 3; // 선의 두께 설정
    ctx.stroke();
    
    // 데이터 포인트 표시
    ctx.fillStyle = "black";
    ctx.font = "12px Arial";
    ctx.textAlign = "center";
    ctx.textBaseline = "bottom";
    for (var i = 0; i < data.data.length; i++) {
        var x = 80 + i * 50;
        var y = graphTop + graphHeight * (1 - ((data.data[i].amount - minValue) / (maxValue - minValue)));
        ctx.fillText(data.data[i].amount, x, y - 10); // 해당 값 표시 -10이면 y값의 10px위에 표시
    }
}

drawGraph();