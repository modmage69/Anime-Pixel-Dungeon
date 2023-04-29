/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2023 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.qsr.customspd.windows;

import com.qsr.customspd.Rankings;
import com.qsr.customspd.ShatteredPixelDungeon;
import com.qsr.customspd.assets.Asset;
import com.qsr.customspd.assets.GeneralAsset;
import com.qsr.customspd.messages.Messages;
import com.qsr.customspd.scenes.PixelScene;
import com.qsr.customspd.ui.IconButton;
import com.qsr.customspd.ui.RenderedTextBlock;
import com.qsr.customspd.ui.ScrollPane;
import com.qsr.customspd.ui.Window;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WndDailies extends Window {

	private static final int WIDTH			= 115;
	private static final int HEIGHT			= 144;

	public WndDailies(){

		resize(WIDTH, HEIGHT);

		ScrollPane pane = new ScrollPane(new Component());
		add(pane);
		pane.setRect(0, 0, WIDTH, HEIGHT);

		Component content = pane.content();

		IconTitle title = new IconTitle(new Image(Asset.getAssetFileHandle(GeneralAsset.ICON_CALENDAR)), Messages.get(this, "title"));
		title.imIcon.hardlight(0.5f, 1f, 2f);
		title.setRect(0, 0, WIDTH, 0);
		title.setPos(0, 0);
		content.add(title);

		if (Rankings.INSTANCE.latestDailyReplay != null){
			IconButton replayInfo = new IconButton(new Image(Asset.getAssetFileHandle(GeneralAsset.ICON_CALENDAR))){
				@Override
				protected void onClick() {
					ShatteredPixelDungeon.scene().addToFront(new WndRanking(Rankings.INSTANCE.latestDailyReplay));
				}

				@Override
				protected void onPointerUp() {
					super.onPointerUp();
					icon.hardlight(1f, 0.5f, 2f);
				}
			};
			replayInfo.icon().hardlight(1f, 0.5f, 2f);
			replayInfo.setRect(WIDTH-16, 0, 16, 16);
			add(replayInfo);
		}

		int top = (int)title.bottom()+3;

		RenderedTextBlock day = PixelScene.renderTextBlock(Messages.get(this, "date"), 7);
		day.hardlight(TITLE_COLOR);
		day.setPos(0, top);
		content.add(day);

		RenderedTextBlock score = PixelScene.renderTextBlock(Messages.get(this, "score"), 7);
		score.hardlight(TITLE_COLOR);
		score.setPos(WIDTH - score.width(), top);
		content.add(score);

		top = (int) score.bottom() + 6;

		NumberFormat num = NumberFormat.getInstance(Locale.US);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date date = new Date();

		//reverse order so that latest dailies are on top
		ArrayList<Long> dates = new ArrayList<>(Rankings.INSTANCE.dailyScoreHistory.keySet());
		Collections.reverse(dates);

		boolean first = Rankings.INSTANCE.latestDaily != null;
		for (long l : dates) {
			if (first) top += 2;

			ColorBlock sep = new ColorBlock(WIDTH, 1, 0xFF000000);
			sep.y = top - 3 - (first ? 2 : 0);
			content.add(sep);

			date.setTime(l);
			day = PixelScene.renderTextBlock(format.format(date), 7);
			day.setPos(0, top);
			content.add(day);

			if (first){
				IconButton latestInfo = new IconButton(new Image(Asset.getAssetFileHandle(GeneralAsset.ICON_INFO))){
					@Override
					protected void onClick() {
						ShatteredPixelDungeon.scene().addToFront(new WndRanking(Rankings.INSTANCE.latestDaily));
					}
				};
				latestInfo.setRect(day.right()+2, top - 5, 16, 16);
				content.add(latestInfo);
			}

			score = PixelScene.renderTextBlock(num.format(Rankings.INSTANCE.dailyScoreHistory.get(l)), 7);
			score.setPos(WIDTH - score.width(), top);
			content.add(score);

			top = (int) day.bottom() + 6;
			if (first){
				top += 2;
				first = false;
			}
		}

		content.setRect(0, 0, WIDTH, top);
		pane.setRect(0, 0, WIDTH, HEIGHT);

	}

}
