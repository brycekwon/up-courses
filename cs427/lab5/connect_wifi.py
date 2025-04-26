import network
import socket
import time

SSID="portlandia"                                         
PASSWORD="PLUMtree44"                                
wlan=None
soc=None

def connectWifi(ssid,passwd):
  global wlan
  wlan=network.WLAN(network.STA_IF)                    
  wlan.active(True)                                     
  wlan.disconnect()                                   
  wlan.connect(ssid,passwd)                           
  while(wlan.ifconfig()[0]=='0.0.0.0'):
    time.sleep(1)
  print(wlan.ifconfig())
  return True


connectWifi(SSID, PASSWORD)