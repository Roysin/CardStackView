package org.roysin.cardstackview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.roysin.cardstackview.cardData.CardInfo;
import org.roysin.cardstackview.view.CardStackAdapter;
import org.roysin.cardstackview.view.CardStackView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CardStackView.StateChangedListener {

    TextView tvState ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvState = ((TextView)findViewById(R.id.tv_state));

        CardStackView stackView = (CardStackView) findViewById(R.id.card_stack_view);
        CardStackAdapter adapter = new CardStackAdapter(MainActivity.this);
        adapter.setData(getAddedCards());
        stackView.setAdapter(adapter);

        stackView.setOnStateChangedListener(this);
    }

    private List<CardInfo> getAddedCards() {
        List<CardInfo> cards = new ArrayList<>();
        String prefix = "6000223404";
        for(int i=0;i<4;i++){
            CardInfo info = new CardInfo(prefix+i+i);
            cards.add(info);
        }
        return cards;
    }

    @Override
    public void OnStackViewStateChanged(int stateChangedTo, int clickIndex) {
        switch(stateChangedTo){
            case CardStackView.STATE_FOCUS:
                tvState.setVisibility(View.VISIBLE);
                tvState.setText("FOCUS ON: "+ (clickIndex+1));
                break;
            case CardStackView.STATE_EXPANDED:
            case CardStackView.STATE_NORMAL:
                tvState.setVisibility(View.INVISIBLE);
        }
    }
}
