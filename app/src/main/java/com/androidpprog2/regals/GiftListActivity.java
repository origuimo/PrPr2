package com.androidpprog2.regals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GiftListActivity extends AppCompatActivity {

    private static  GiftListActivity sLlista;
    private List<Gift> lgifts;

        public static GiftListActivity getInstance(Context context){
            if(sLlista == null){
                sLlista = new GiftListActivity(context);
            }
            return sLlista;
        }
        private GiftListActivity(Context context){
            lgifts= new ArrayList<>();
            for(int i = 0; i<100;i++){
                Gift gift = new Gift();
                //Aixo s'haura d'omplir amb el resultat del get de la api quan el fem
                //gift.setName("Regal # " + i);
                //gift.setDate();
                //gift.setLink();
                //gift.setWishList();
                //gift.setReserved();
                lgifts.add(gift);

            }

        }
        public List <Gift> getGifts(){
            return lgifts;
        }

        public Gift getGift(int id) {
            return lgifts.get(id);
        }
}
