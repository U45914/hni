SET MODE MySQL;
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Table `event_state`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `event_state` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `eventname` VARCHAR(255) NOT NULL,
  `phoneno` VARCHAR(45) NOT NULL UNIQUE,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `registration_state`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `registration_state` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `eventname` VARCHAR(255) NOT NULL,
  `phoneno` VARCHAR(45) NOT NULL,
  `payload` VARCHAR(255) NULL,
  `eventstate` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(255) NULL,
  `last_name` VARCHAR(255) NULL,
  `gender_code` VARCHAR(1) NULL,
  `mobile_phone` VARCHAR(45) NULL,
  `email` VARCHAR(255) NULL,
  `deleted` INT NULL,
  `hashed_secret` VARCHAR(255) NULL,
  `salt` VARCHAR(255) NULL,
  `created` DATETIME NULL,
  `opt_out` INT NULL DEFAULT 0 COMMENT 'true/false whether the user is opt-in/out.  Default ‘0’',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `organizations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `organizations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `phone` VARCHAR(45) NULL,
  `email` VARCHAR(255) NULL,
  `website` VARCHAR(255) NULL,
  `logo` VARCHAR(255) NULL,
  `created` DATETIME NOT NULL,
  `created_by` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `security_roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `security_roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_organization_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_organization_role` (
  `user_id` INT NOT NULL,
  `organization_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `organization_id`, `role_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `providers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `providers` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `address_id` INT NULL,
  `menu_id` INT NOT NULL,
  `website_url` VARCHAR(255) NULL,
  `created` DATETIME NOT NULL,
  `created_by` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `provider_locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `provider_locations` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `provider_id` INT NOT NULL,
  `address_id` INT NULL,
  `created` DATETIME NOT NULL,
  `created_by` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `provider_location_id` INT NOT NULL,
  `order_date` DATETIME NOT NULL,
  `ready_date` DATETIME NULL,
  `pickup_date` DATETIME NULL,
  `subtotal` DECIMAL(10,2) NULL,
  `tax` DECIMAL(10,2) NULL,
  `created_by` INT NULL COMMENT 'surrogate to users',
  `status_id` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menus` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `provider_id` INT NOT NULL,
  `start_hour` INT NULL COMMENT 'starting hour item is available in 24hr',
  `end_hour` INT NULL COMMENT 'ending hour item is available in 24hr',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menu_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `menu_items` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `menu_id` INT NOT NULL,
  `name` VARCHAR(255) NULL,
  `description` TEXT NULL,
  `price` DECIMAL(10,2) NULL,
  `expires` DATETIME NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `order_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `order_id` INT NOT NULL,
  `menu_item_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `amount` DECIMAL(10,2) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `addresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `addresses` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NULL,
  `address_line1` VARCHAR(255) NULL,
  `address_line2` VARCHAR(255) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(2) NULL,
  `zip` VARCHAR(15) NULL,
  `longitude` DOUBLE NULL,
  `latitude` DOUBLE NULL,
  `timezone` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `activation_codes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `activation_codes` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `activation_code` VARCHAR(128) NOT NULL,
  `organization_id` INT NOT NULL,
  `meals_authorized` INT NULL,
  `meals_remaining` INT NULL,
  `enabled` TINYINT NULL COMMENT 'true/false whether this voucher can be used',
  `comments` VARCHAR(255) NULL,
  `created` VARCHAR(45) NULL,
  `user_id` INT NULL COMMENT 'the user who “owns” this voucher',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_activation_code` (`activation_code`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_provider_role`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_provider_role` (
  `user_id` INT NOT NULL,
  `provider_id` INT NOT NULL,
  `role_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `provider_id`, `role_id`))
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `organization_addresses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `organization_addresses` (
  `organization_id` INT NOT NULL,
  `address_id` INT NOT NULL,
  PRIMARY KEY (`organization_id`, `address_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `provider_location_hours`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `provider_location_hours` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `provider_location_id` INT NOT NULL,
  `dow` VARCHAR(3) NULL,
  `open_hour` INT NULL COMMENT 'open hour in 24hr',
  `close_hour` INT NULL COMMENT 'close hour in 24hr',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `security_permissions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `security_permissions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `domain` VARCHAR(45) NULL,
  `value` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `security_role_permissions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `security_role_permissions` (
  `role_id` INT NOT NULL,
  `permission_id` INT NOT NULL,
  `all_instances` INT NULL DEFAULT 0,
  PRIMARY KEY (`role_id`, `permission_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `payment_instruments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `payment_instruments` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `provider_id` INT NULL,
  `card_type` VARCHAR(45) NULL,
  `card_serial_id` VARCHAR(255) NULL,  
  `card_number` VARCHAR(45) NULL COMMENT 'hashed value',
  `status` VARCHAR(45) NULL COMMENT 'activated or not',
  `orginal_balance` DECIMAL(10,2) NULL,
  `balance` DECIMAL(10,2) NULL,
  `last_used_datetime` DATETIME NULL,
  `pin_number` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `order_payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `order_payments` (
  `order_id` INT NOT NULL,
  `payment_instrument_id` INT NOT NULL,
  `amount` DECIMAL(10,2) NULL,
  `created_by` INT NOT NULL,
  `created_date` DATETIME NOT NULL,
  PRIMARY KEY (`order_id`, `payment_instrument_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `partial_orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partial_orders` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NULL,
  `provider_location_id` INT NULL,
  `menu_item_id` INT NULL,
  `chosen_menu_id` INT NULL,
  `chosen_provider_id` INT NULL,
  `transaction_phase` VARCHAR(45) NULL,
  `address` VARCHAR(160) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `partial_orders_menu_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partial_orders_menu_items` (
  `id` INT NOT NULL,
  `menu_item_id` INT NOT NULL)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `partial_orders_provider_locations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partial_orders_provider_locations` (
  `id` INT NOT NULL,
  `provider_location_id` INT NOT NULL)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `partial_orders_menu_selections`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partial_orders_menu_selections` (
  `id` INT NOT NULL,
  `menu_item_id` INT NOT NULL)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `board_members`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `board_members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `company` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT ;


-- -----------------------------------------------------
-- Table `brand_partners`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `brand_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `company` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `client`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL DEFAULT '0',
  `created_by` int(11) NOT NULL DEFAULT '0',
  `race` int(11) NOT NULL DEFAULT '0',
  `address_id` int(11) NOT NULL DEFAULT '0',
  `bday` int(11) DEFAULT '0',
  `been_arrested` char(1) DEFAULT '0',
  `been_convicted` char(1) DEFAULT '0',
  `has_smart_phone` char(1) DEFAULT '0',
  `service_provider` varchar(50) DEFAULT '0',
  `model` varchar(50) DEFAULT '0',
  `have_monthly_plan` char(1) DEFAULT '0',
  `monthly_plan_minute` varchar(50) DEFAULT '0',
  `monthly_plan_data` varchar(50) DEFAULT '0',
  `monthly_plan_cost` varchar(50) DEFAULT '0',
  `alt_monthly_plan` int(11) DEFAULT '0',
  `alt_monthly_plan_together` varchar(50) DEFAULT '0',
  `sliblings` int(11) DEFAULT '0',
  `kids` int(11) DEFAULT '0',
  `live_at_home` char(1) DEFAULT '0',
  `sheltered` int(11) DEFAULT '0',
  `parent_education` int(11) DEFAULT '0',
  `education` int(11) DEFAULT '0',
  `enrollment_status` int(11) DEFAULT '0',
  `enrollment_location` varchar(50) DEFAULT '0',
  `work_status` int(11) DEFAULT '0',
  `time_to_workplace` int(11) DEFAULT '0',
  `no_of_job` int(11) DEFAULT '0',
  `employer` varchar(50) DEFAULT '0',
  `job_title` varchar(50) DEFAULT '0',
  `duration_of_employement` int(11) DEFAULT '0',
  `unemployment_benfits` char(1) DEFAULT '0',
  `reason_unemployment_benefits` varchar(100) DEFAULT '0',
  `total_income` double DEFAULT '0',
  `rate_amount` int(11) DEFAULT '0',
  `rate_type` int(11) DEFAULT '0',
  `avg_hours_per_week` varchar(255) DEFAULT '0',
  `resident_status` int(11) DEFAULT '0',
  `dollar_spend_food` int(11) DEFAULT '0',
  `dollar_spend_clothes` int(11) DEFAULT '0',
  `dollar_spend_entertainment` int(11) DEFAULT '0',
  `dollar_spend_transport` int(11) DEFAULT '0',
  `dollar_spend_savings` int(11) DEFAULT '0',
  `meals_per_day` int(11) DEFAULT '0',
  `food_preference` int(11) DEFAULT '0',
  `food_source` varchar(50) DEFAULT '0',
  `cook` char(1) DEFAULT '0',
  `travel_for_food_distance` int(11) DEFAULT '0',
  `traval_for_food_time` int(11) DEFAULT '0',
  `sub_food_program` char(1) DEFAULT '0',
  `sub_food_program_entity` varchar(50) DEFAULT '0',
  `sub_food_program_duration` int(11) DEFAULT '0',
  `sub_food_program_renew` int(11) DEFAULT '0',
  `sub_food_program_exp` varchar(256) DEFAULT '0',
  `allergies` varchar(256) DEFAULT '0',
  `addiction` char(1) DEFAULT '0',
  `addiction_type` varchar(50) DEFAULT '0',
  `mental_health_issue` char(1) DEFAULT '0',
  `mental_health_issue_history` varchar(256) DEFAULT '0',
  `height` varchar(50) DEFAULT '0',
  `weight` varchar(50) DEFAULT '0',
  `exercise_per_week` int(11) DEFAULT '0',
  `last_visit_doctor` int(11) DEFAULT '0',
  `last_visit_dentist` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `education`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `education` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `education_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `food_bank`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `food_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `food_bank_name` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `food_services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `food_services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `service_type` int(11) NOT NULL,
  `weekdays` varchar(150) NOT NULL,
  `total_count` int(11) NOT NULL,
  `other` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `hni_services`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hni_services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `service_name` varchar(75) NOT NULL,
  `service_path` varchar(100) NOT NULL,
  `service_img` varchar(500) DEFAULT NULL,
  `active` varchar(1) NOT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT ;

-- -----------------------------------------------------
-- Table `income`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `income` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `income_desc` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `invitation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `invitation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_id` int(11) NOT NULL,
  `invite_code` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `invited_by` int(11) NOT NULL,
  `token_expire_date` date NOT NULL,
  `created_date` date NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT ;

-- -----------------------------------------------------
-- Table `local_partners`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `local_partners` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `phone` varchar(45) NOT NULL,
  `company` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `marital_status`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `marital_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marital_status_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT ;
-- -----------------------------------------------------
-- Table `meal_donation_sources`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `meal_donation_sources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `source` varchar(255) NOT NULL,
  `frequency` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `meal_funding_sources`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `meal_funding_sources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `source` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `ngo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ngo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  `website` varchar(255) NOT NULL,
  `fte` int(11) NOT NULL,
  `overview` varchar(2048) NOT NULL,
  `mission` varchar(2048) NOT NULL,
  `monthly_budget` int(11) NOT NULL,
  `operating_cost` int(11) DEFAULT NULL,
  `personal_cost` int(11) DEFAULT NULL,
  `kitchen_volunteers` int(11) DEFAULT NULL,
  `food_stamp_assist` tinyint(1) NOT NULL,
  `food_bank` tinyint(1) NOT NULL,
  `resources_to_clients` int(11) NOT NULL,
  `ind_serv_daily` int(11) NOT NULL,
  `ind_serv_monthly` int(11) NOT NULL,
  `ind_serv_annual` int(11) NOT NULL,
  `client_info` tinyint(1) NOT NULL,
  `store_client_info` varchar(255) DEFAULT NULL,
  `clients_unsheltered` int(11) NOT NULL,
  `clients_employed` int(11) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `ngo_funding_sources`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ngo_funding_sources` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ngo_id` int(11) NOT NULL,
  `amount` double NOT NULL,
  `source` varchar(255) NOT NULL,
  `created` datetime NOT NULL,
  `created_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `partial_orders_order_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `partial_orders_order_items` (
  `id` int(11) NOT NULL,
  `order_item_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `race`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `race` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `race_desc` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `user_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_address` (
  `user_id` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`address_id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `user_profile_tmp`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_profile_tmp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `data` blob NOT NULL,
  `created` datetime NOT NULL,
  `last_updated` datetime NOT NULL,
  `status` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `user_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `user_status` (
  `user_id` int(11) NOT NULL,
  `status` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `volunteer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `volunteer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `created` date NOT NULL,
  `created_by` int(11) NOT NULL,
  `address_id` int(11) NOT NULL,
  `birthday` date NOT NULL,
  `sex` char(1) NOT NULL,
  `race` int(11) NOT NULL,
  `education` int(11) NOT NULL,
  `marital_status` int(11) NOT NULL,
  `income` int(11) NOT NULL,
  `kids` int(11) NOT NULL,
  `employer` varchar(100) NOT NULL,
  `non_profit` char(1) NOT NULL COMMENT 'Yes or No',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT ;

-- -----------------------------------------------------
-- Table `volunteer_availability`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `volunteer_availability` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` date NOT NULL,
  `created_by` int(11) NOT NULL,
  `volunteer_id` int(11) NOT NULL,
  `timeline` int(11) NOT NULL COMMENT 'It should be a list of time rages, constants',
  `weekday` varchar(50) NOT NULL COMMENT 'sunday, monday, etc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT ;