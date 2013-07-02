package com.socialbar.android.activities;

import com.socialbar.android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		Button radar = (Button) findViewById(R.id.newbar);
		radar.setOnClickListener(this);
	}

	@Override
	public void onResume() {
		super.onResume();
		this.BackToFront();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.newbar:
			Intent intent = new Intent(this, BarProfileActivity.class);
			startActivity(intent);
			this.NewChild();
			break;
		default:
			break;
		}
	}
	
	/**
	 * variavel para marcar a chamada de um filho
	 */
	private boolean child;
	/**
	 * metodo para executar quando um filho é chamado
	 */
	private void NewChild(){
		this.child = true;
		this.overridePendingTransition(R.anim.in_enter, R.anim.in_leave);
	}
	/**
	 * Metodo executado quando o app volta a funcionar, seja pelo retorno de um filho, seja pelo sistema operacional
	 * BUG: para de funcionar quando a tela é rotacionada
	 */
	private void BackToFront(){
		if(child){
			//this.overridePendingTransition(R.anim.out_enter, R.anim.out_leave);
			this.child = false;
		}
	}
}
