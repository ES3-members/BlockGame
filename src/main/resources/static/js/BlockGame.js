let canvas = document.getElementById("canvas");
let ctx = canvas.getContext("2d");

/********************
 ボールの設定
 ********************/
const BALL_COLOR = 'skyblue'; //ボールの色
const BALL_RADIUS = 10; //ボールの大きさ（半径）
let ballX = canvas.width / 2; //ゲーム開始時のX軸（←→）の位置
let ballY = canvas.height - 30; //ゲーム開始時のY軸（↑↓）の位置
let xMove = 2;  //X方向への移動量
let yMove = 2;  //Y方向への移動量

/********************
 操作する板の設定
 ********************/
const BAR_COLOR = 'blue'; //板の色
const BAR_HEIGHT = 10;  //板の高さ
const BAR_WIDTH = 75; //板の幅
let barX = (canvas.width - BAR_WIDTH) / 2;
let rigthKeyFlag = false;
let leftKeyFlag = false;

/********************
 ブロックの設定
 ********************/
const BLOCK_COLOR = 'orange'
const BLOCK_ROW_COUNT = 3; //ブロックの縦の数
const BLOCK_COLUMN_COUNT = 5; //ブロックの横の数
const BLOCK_WIDTH = 75; //ブロックの幅
const BLOCK_HEGHT = 20; //ブロックの高さ
const BLOCK_MARGIN = 10; //ブロックとブロックの隙間
const BLOCK_AREA_MARGIN = 30; //ブロックを並べる領域と画面の隙間
let blockArray = []; //配置したブロック位置の配列
for(let i = 0; i < BLOCK_ROW_COUNT * BLOCK_COLUMN_COUNT; i++){
  let row = Math.floor(i/BLOCK_COLUMN_COUNT);
  let column = i - (BLOCK_COLUMN_COUNT * row); 
  blockArray.push( {
    x : BLOCK_AREA_MARGIN + BLOCK_MARGIN * column + BLOCK_WIDTH * column,
    y : BLOCK_AREA_MARGIN + BLOCK_MARGIN * row +  BLOCK_HEGHT * row
    } );
}

function drawBall() {
  ballX += xMove;
  ballY += yMove;
  ctx.beginPath();
  ctx.arc(ballX, ballY, BALL_RADIUS, 0, Math.PI * 2);
  ctx.fillStyle = BALL_COLOR;
  ctx.fill();
  ctx.closePath();
  if (ballX + xMove > canvas.width - BALL_RADIUS || ballX + xMove < BALL_RADIUS) {
    xMove = -xMove;
  }
  if (ballY + yMove < BALL_RADIUS) {
    yMove = -yMove;
  } else if (ballY + yMove > canvas.height - BALL_RADIUS) {
    if (ballX > barX && ballX < barX + BAR_WIDTH) {
      yMove = -yMove;
    } else {
      console.log("ゲームオーバー");
      clearInterval(interval)
    }
  }
}

function drawBlock() {
  blockArray = blockArray.filter( (block, index) => {
    if (ballX > block.x && ballX < block.x + BLOCK_WIDTH && ballY > block.y && ballY < block.y + BLOCK_HEGHT) {
      yMove = -yMove;
    }else{
      return block;
    }
  });
  if(blockArray.length === 0){
    console.log('ゲームクリア');
    clearInterval(interval);
    return;
  }
  blockArray.forEach((block) => {
    ctx.beginPath();
    ctx.rect(block.x, block.y, BLOCK_WIDTH, BLOCK_HEGHT);
    ctx.fillStyle = BLOCK_COLOR;
    ctx.fill();
    ctx.closePath();
  });
}

function drawBar() {
  if (rigthKeyFlag && barX < canvas.width - BAR_WIDTH) {
    barX += 7;
  } else if (leftKeyFlag && barX > 0) {
    barX -= 7;
  }
  ctx.beginPath();
  ctx.rect(barX, canvas.height - BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);
  ctx.fillStyle = BAR_COLOR;
  ctx.fill();
  ctx.closePath();
}

function keyDownHandler(e) {
  if (e.key == "Right" || e.key == "ArrowRight") {
    rigthKeyFlag = true;
  } else if (e.key == "Left" || e.key == "ArrowLeft") {
    leftKeyFlag = true;
  }
}

function keyUpHandler(e) {
  if (e.key == "Right" || e.key == "ArrowRight") {
    rigthKeyFlag = false;
  } else if (e.key == "Left" || e.key == "ArrowLeft") {
    leftKeyFlag = false;
  }
}

function draw() {
  ctx.clearRect(0, 0, canvas.width, canvas.height);
  drawBall();
  drawBlock();
  drawBar();
}

document.addEventListener("keydown", keyDownHandler);
document.addEventListener("keyup", keyUpHandler);
let interval = setInterval(draw, 10);