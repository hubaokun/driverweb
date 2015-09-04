// JavaScript Document


var date = new Date();

date  = date.valueOf()
date  = date ;
date  = new Date(date);

year = date.getFullYear();
month = date.getMonth() + 1;
date = date.getDate();
document.getElementById("HiddenDate").innerText = year + "/" + month + "/" + date + " 00:00:00";
InitDate();


function TurnLeft()
{
var date = new Date(document.getElementById("HiddenDate").innerText);
date  = date.valueOf()
date  = date  - 5 * 24 * 60 * 60 * 1000;
date  = new Date(date);
year = date.getFullYear();
month = date.getMonth() + 1;
date = date.getDate();
document.getElementById("HiddenDate").innerText = year + "/" + month + "/" + date + " 00:00:00";
InitDate();
}

function TurnRight()
{
var date = new Date(document.getElementById("HiddenDate").innerText);
date  = date.valueOf()
date  = date  + 5 * 24 * 60 * 60 * 1000;
date  = new Date(date);
year = date.getFullYear();
month = date.getMonth() + 1;
date = date.getDate();
document.getElementById("HiddenDate").innerText = year + "/" + month + "/" + date + " 00:00:00";
InitDate();
}

function InitDate()
{
for(var i = 1;i < 6;i++)
{
document.getElementById("custom" + i).className = "";
}
for(var i = 1;i <= 5;i++)
{
date = document.getElementById("HiddenDate").innerText;
document.getElementById("custom" + i).innerText = GetDate(date,i - 1);
}

}

function GetDate(currentDate,num)
{
var date = new Date(currentDate);
date  = date.valueOf()
date  = date + num * 24 * 60 * 60 * 1000;
date  = new Date(date);
this.year = date.getFullYear();
this.month = date.getMonth() + 1;
this.date = date.getDate();
this.day = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六")[date.getDay()];
this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
var currentTime = this.month + "-" + this.date ;
return currentTime;
}

