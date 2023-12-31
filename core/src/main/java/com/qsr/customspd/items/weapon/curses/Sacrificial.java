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

package com.qsr.customspd.items.weapon.curses;

import com.qsr.customspd.actors.Char;
import com.qsr.customspd.actors.buffs.Bleeding;
import com.qsr.customspd.actors.buffs.Buff;
import com.qsr.customspd.items.weapon.Weapon;
import com.qsr.customspd.sprites.ItemSprite;
import com.watabou.utils.Random;

public class Sacrificial extends Weapon.Enchantment {

	private static ItemSprite.Glowing BLACK = new ItemSprite.Glowing( 0x000000 );

	@Override
	public int proc(float probability, int strength, Char attacker, Char defender, int damage) {
		if (Random.Float() < probability) {
			float missingPercent = attacker.HP/(float)attacker.HT;
			float bleedAmt = (float)(Math.pow(missingPercent, 2) * attacker.HT)/5;
			Buff.affect(attacker, Bleeding.class).set(Math.max(1, bleedAmt), getClass());
		}

		return damage;
	}

	@Override
	public int proc(Weapon weapon, Char attacker, Char defender, int damage ) {
		return proc(1/12f * procChanceMultiplier(attacker), 0, attacker, defender, damage);
	}

	@Override
	public boolean curse() {
		return true;
	}

	@Override
	public ItemSprite.Glowing glowing() {
		return BLACK;
	}

}
