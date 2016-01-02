/**
 * WL Multi Language Support Plugin
 * Copyright (C) 2013 MK124
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.gtaun.wl.lang;

import net.gtaun.shoebill.resource.Plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LanguagePlugin extends Plugin
{
	public static final Logger LOGGER = LoggerFactory.getLogger(LanguagePlugin.class);

	private LanguageServiceImpl service;

	@Override
	protected void onEnable() throws Throwable
	{
		service = new LanguageServiceImpl(getEventManager());
		registerService(LanguageService.class, service);
		
		LOGGER.info(getDescription().getName() + " " + getDescription().getVersion() + " enabled.");
	}
	
	@Override
	protected void onDisable() throws Throwable
	{
		unregisterService(LanguageService.class);
		
		service.destroy();
		service = null;
		
		LOGGER.info(getDescription().getName() + " " + getDescription().getVersion() + " disabled.");
	}
}
