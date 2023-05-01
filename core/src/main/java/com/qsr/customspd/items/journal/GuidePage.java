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

package com.qsr.customspd.items.journal;

import com.qsr.customspd.journal.Document;
import com.qsr.customspd.messages.Messages;
import com.qsr.customspd.assets.GeneralAsset;

public class GuidePage extends DocumentPage {
	
	{
		image = GeneralAsset.GUIDE_PAGE;
	}
	
	@Override
	public Document document() {
		return Document.ADVENTURERS_GUIDE;
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", document().pageTitle(page()));
	}
}
