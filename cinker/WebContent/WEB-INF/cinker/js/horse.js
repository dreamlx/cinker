/*
 * $prizes表示奖品的块
 * changeClass 表示会跳动变化的类
 * prizeArr表示奖品跳动的数组
 * prizeNum 表示获得的奖品
var num = 0; //当前点亮的灯
var circle = 0; //至少转跑马灯的圈数
var t; //定时器
var len;//奖品个数
 */
 var num = 0; //当前点亮的灯
 var circle = 0; //至少转跑马灯的圈数
function lightChange($prizes, changeClass, prizeArr, prizeNum){
  var self = this;
  len = $prizes.length;
  $prizes.removeClass(changeClass);
  $prizes.eq(prizeArr[num]).addClass(changeClass);
  if(num == len-1){
	num = 0;
	circle ++;
  } else {
	num ++;
  }
  if(circle == 2 && num == prizeNum){
	circle = 0;
	clearTimeout(t);
	showAll('#xbtc-cont');
  } else {
	t = setTimeout(function(){lightChange($prizes, changeClass, prizeArr, prizeNum)},200); 
  }
}