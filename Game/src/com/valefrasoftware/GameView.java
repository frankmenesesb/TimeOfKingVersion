/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.valefrasoftware;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.valefrasoftware.control.Printer;
import java.util.ArrayList;
import java.util.List;

 
 
public class GameView extends SurfaceView {
       private Bitmap bmp;
       private Bitmap analogo;
       private Bitmap botonA;
       private Bitmap botonB;
       private Bitmap fondo;
       private Bitmap viejo;
       private Bitmap dialogo;
       private Bitmap outh;
       private Bitmap ninja;
       private Bitmap transparente;
       private int conDialogo=0;
       private int intro=0;
       private SurfaceHolder holder;
       private GameLoopThread gameLoopThread;
       private int x = getWidth()/2;
       private int y= getHeight()/2;
       private int xSpeed = 1;
       private int ySpeed = 1;
       private long lastClick;
       private int contador=0;
       private int contadorArriba=0;
       private int contadorLeft=0;
       private int contadorRigth=0;
       private int xBoton=10;
       private int yBoton=10;
       private int xFondo=-20;
       private int yFondo=-20;
       private Bitmap arbolito;
       boolean caminando;
       boolean inicio_bmp=true;
       boolean caminandoArriba;
       boolean rigth;
       boolean left;
       boolean stop;
       boolean espada;
       Printer pin;
       int movimientox;
       int movimientoy;
       ArrayList arbolitos;
       int xo=20;
       int yo=20;
       int contadorEspada=0;
      
       public GameView(Context context) {
             super(context);
             gameLoopThread = new GameLoopThread(this);
             holder = getHolder();
             holder.addCallback(new SurfaceHolder.Callback() {
 
                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                           boolean retry = true;
                           gameLoopThread.setRunning(false);
                           while (retry) {
                                  try {
                                        gameLoopThread.join();
                                        retry = false;
                                  } catch (InterruptedException e) {
                                  }
                           }
                    }
 
                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                           gameLoopThread.setRunning(true);
                           gameLoopThread.start();
                    }
 
                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format,
                                  int width, int height) {
                    }
                    
                    
                    
             });
             // se declara arreglo para reproducir arboles
             arbolitos = new ArrayList();
             
             //se instancian las bitmap llamando las imagenes que le corresponden
             bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
             analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_1);
             botonA=BitmapFactory.decodeResource(getResources(), R.drawable.boton_a);
             botonB=BitmapFactory.decodeResource(getResources(), R.drawable.boton_b);
             fondo = BitmapFactory.decodeResource(getResources(), R.drawable.tierra);
             viejo = BitmapFactory.decodeResource(getResources(), R.drawable.viejo);
             dialogo = BitmapFactory.decodeResource(getResources(), R.drawable.transpar);
             arbolito = BitmapFactory.decodeResource(getResources(), R.drawable.arbolillo);
             outh = BitmapFactory.decodeResource(getResources(), R.drawable.outh);
             ninja = BitmapFactory.decodeResource(getResources(), R.drawable.ninja_left_ata);
             //se el envia varios arboles a el arreglo arbolitos
             for(int q=0; q<20; q++){
                 arbolitos.add(q, arbolito);
             }
             
              
       }

       
       
 
       @Override
       protected void onDraw(Canvas canvas) {
           System.out.println(System.getenv());
           //int movimientox;
           
             
             
             // el if para la imagen inicial
        
      if(!stop){
          
          
           if(caminando){
            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_down);
            ySpeed=+10;
            contador++;
            
            if (contador==1){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_left);
            }

            if (contador==2){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.down_right);
                contador=0;
            }
               
        }
           
            
        
        //caminando arriba
        
        if(caminandoArriba){
            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_up);
            ySpeed=-10;
            contadorArriba++;
            
            if (contadorArriba==1){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.up_left);
            }
            if (contadorArriba==2){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.up_right);
                contadorArriba=0;
            }
         }
           
        
        
        //caminando derecha
        if(rigth){
            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_rigth);
            xSpeed = 10;
               contadorRigth++;
            if (contadorRigth==1){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_left);
            }
            if (contadorRigth==2){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_left_2);
            contadorRigth=0;
            }
        }
           
        
        
        
        //caminando izquierda
        if(left){
            
            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_left);
            xSpeed = -10;  
            contadorLeft++;
            if (contadorLeft==1){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_rigth);
            }
            if (contadorLeft==2){
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_right_2);
            contadorLeft=0;
            }
           }
        }
           
             intro++;
             int abueloX, abueloY, ninjaX, ninjaY;
             
             y=y+ySpeed;
             x = x + xSpeed;
             movimientox=xFondo-x;
             movimientoy=yFondo-y;
             abueloX=movimientox+400;
             abueloY=movimientoy+190;
             ninjaX=movimientox+460;
             ninjaY=movimientoy+310;
             canvas.drawColor(Color.BLACK);
             canvas.drawBitmap(fondo, movimientox, movimientoy, null);
             canvas.drawBitmap(viejo, abueloX, abueloY, null);
             canvas.drawBitmap(ninja, ninjaX, ninjaY, null);
             canvas.drawBitmap(outh, abueloX+40, abueloY, null);
             canvas.drawBitmap(arbolito, movimientox+40 , movimientoy+40, null);
             
             
//             if(abueloX<=(getWidth()/2) && abueloY>=(getHeight()/2)+20 ){
                 
//             }
//             for(int u=0; u<2; u++){
//             for(int o=0; o<2; o++){
//                xo=100*o;
//                //z=100*u;
//                //publico=movimiento+n;
//                 canvas.drawBitmap((Bitmap) arbolitos.get(o), movimientox+xo , movimientoy+40, null);
//             }
//             }
             //reproduccion de arboles horizontales
             for(int g=0; g<3; g++){
                for(int h=0; h<20; h++){
                 xo=40*h;
                 yo=40*g;
                 canvas.drawBitmap((Bitmap) arbolitos.get(h), movimientox+xo , movimientoy+yo, null);
                }
             }
             
             //reproduccion de arboles verticales
             for(int g=0; g<17; g++){
                for(int h=0; h<7; h++){
                 xo=40*h;
                 yo=40*g;
                 canvas.drawBitmap((Bitmap) arbolitos.get(h), movimientox+xo , movimientoy+yo, null);
                }
             }
             //canvas.drawBitmap(analogo, getWidth() - analogo.getWidth() , getHeight()- analogo.getHeight(), null);
             canvas.drawBitmap(analogo, (getWidth()-getWidth()+100) - analogo.getWidth() , getHeight()- analogo.getHeight(), null);
             
             
             
             /// BMP
             
             canvas.drawBitmap(bmp, getWidth()/2 , getHeight()/2, null);
             ///ATAQUE-------------
             
             if(espada){
                 contadorEspada++;
                 
             }
             if(contadorEspada==1){
                
                 
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_right_2_espada);
                canvas.drawBitmap(bmp, (getWidth()/2)-20 , getHeight()/2, null);
             }
             if(contadorEspada==2){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_right_2_espada_2);
                canvas.drawBitmap(bmp, (getWidth()/2)-20 , getHeight()/2, null);
             }
             if(contadorEspada==3){
                bmp = BitmapFactory.decodeResource(getResources(), R.drawable.air_side_rigth);
                espada=false;
                contadorEspada=0;
                //xSpeed=+20;
                canvas.drawBitmap(bmp, (getWidth()/2)+20 , getHeight()/2, null);
             }
             
             //-----------------------
             
             canvas.drawBitmap(botonA, (getWidth()-getWidth()+400) - botonA.getWidth() , getHeight()- botonA.getHeight(), null);
             //canvas.drawBitmap(botonB, botonB.getWidth()+10 , getHeight()- botonB.getHeight(), null);
             
             xSpeed = 0;
             ySpeed=0;
             //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c1);
             analogo=BitmapFactory.decodeResource(getResources(), R.drawable.anologo_1);
             botonA=BitmapFactory.decodeResource(getResources(), R.drawable.boton_a);
             
             if(conDialogo==1){
                 dialogo = BitmapFactory.decodeResource(getResources(), R.drawable.dialogo12);
             }
             
             
             
             if(conDialogo==2){
               dialogo = BitmapFactory.decodeResource(getResources(), R.drawable.transpar);
               
             }
             if(conDialogo==3){
                 conDialogo=0;
             }
             
             
             
             
                 
                canvas.drawBitmap(dialogo,(getWidth()-getWidth()+450) - dialogo.getWidth()  , getHeight()- getHeight(), null);
             
             
       }

    
    
    
     
       
      
       
       
        @Override
       public boolean onTouchEvent(MotionEvent event) {
    try {
        
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
        case (MotionEvent.ACTION_MOVE): {
            float firstx = event.getHistoricalX(0);
            float firsty = event.getHistoricalY(0);
            float lastx = event.getX();
            float lasty = event.getY();
            
            
            processMovement(firstx, firsty, lastx, lasty);
            pressButon(firstx, firsty, lastx, lasty);

            
            
            
        }break;
        default:{
            stop=true;
            
        }break;
        
        }
        return true;
    } catch (Exception e) {
    
        return false;
   
    }
    
}

private void processMovement(float x1, float y1, float x2, float y2) {
    caminando=false;
    rigth=false;
    left=false;
    stop=false;
    int contador=0;
    caminandoArriba = false;
    
    
    
    if((x2>(getWidth()-getWidth()+100) - analogo.getWidth() && x2<analogo.getWidth()+80) || (y2>getHeight()- analogo.getHeight()) && y2<getHeight()/2){
    
     ///
     if (x2 < x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
        Log.i("touch", "left");
        // move Left
        //setSpriteState(1);
        //x =0;
            //xFondo=+15;
        caminandoArriba=false;
            caminando=false;
            rigth=false;
        if(movimientox<0){
            
            left=true;
        }
            //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
            
            
            
    } else if (x2 > x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
        Log.i("touch", "right");
        // move right
        caminandoArriba=false;
        caminando=false;
        inicio_bmp=false;
        left=false;
        
        //if(movimientox>getWidth()){
        if(movimientox+fondo.getWidth()>getWidth()){
        rigth=true;
        }
        
        //}
        //xFondo=-15;
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c2);
        
        //setSpriteState(2);
    } else if (y2 > y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        Log.i("touch", "down");
        // move down
        //setSpriteState(4);
        caminandoArriba=false;
        rigth=false;
        left=false;
        inicio_bmp=false;
        if(movimientoy+fondo.getHeight()>getHeight()){
        caminando=true;
        }
        
        
        //yFondo=-5;
    } else if (y2 < y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        Log.i("touch", "up");
        
        if(movimientoy<0){
        caminandoArriba=true;//
        }
        caminando=false;
        rigth=false;
        left=false;
        inicio_bmp=false;
        // move up
        
        
        //yFondo=+5;
        //setSpriteState(3);
    }else if (x1==x2 && y1==y2){
        //fire();
   }else{
       
        caminandoArriba=false;
        caminando=false;
        rigth=false;
        left=false;
        stop = true;
        //bmp = BitmapFactory.decodeResource(getResources(), R.drawable.inicio);
        xSpeed = 0; 
   // }
    }
   
//}
    }
    //
}
    
    
    public void pressButon(float x1, float y1, float x2, float y2) {
    if ((x2 > ((getWidth()-getWidth()+400) - botonA.getWidth())  &&  x2<(getWidth()-getWidth()+400)) 
            && (y2>getHeight()- botonA.getHeight()) && y2<getHeight()) {
        //Log.i("touch", "left");
        // move Left
        //setSpriteState(1);
        //x =0;
//            xSpeed = -10;  
//            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c3);
//            analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_left_1);
        botonA=BitmapFactory.decodeResource(getResources(), R.drawable.boton_a_2);
        //conDialogo++;
        espada=true;
        //bmp.setPixel(70, 80, contador);    
    } else if (x2 > x1 && (Math.abs(y2 - y1) < Math.abs(x2 - x1))) {
//        //Log.i("touch", "right");
//        // move right
//        xSpeed = 10;
//        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.c2);
//        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_rigth_1);
        //setSpriteState(2);
    } else if (y2 > y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "down");
        // move down
        //setSpriteState(4);
//        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_down_1);
//        ySpeed=+5;
    } else if (y2 < y1 && (Math.abs(y2 - y1) > Math.abs(x2 - x1))) {
        //Log.i("touch", "up");
                // move up
//        analogo=BitmapFactory.decodeResource(getResources(), R.drawable.analogo_up_1);
//        ySpeed=-5;
        //setSpriteState(3);
    } else if (x1==x2 && y1==y2){
        //fire();
    }
    
    
}

}